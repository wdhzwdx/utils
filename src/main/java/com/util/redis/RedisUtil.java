package com.util.redis;

import com.google.common.collect.Lists;
import com.util.serialize.SerializeUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 
 * jedis客户端工具类
 * @author wdh
 *
 */
public class RedisUtil {
	
    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);
    
    private ShardedJedisPool shardedJedisPool;
    
    public RedisUtil(ShardedJedisPool  shardedJedisPool){
    	this.shardedJedisPool = shardedJedisPool;
    }
    
    /**
     * 字符集
     */
    private final Charset charset = Charset.forName("UTF-8");
    
	public String deserialize(byte[] bytes) {
		return (bytes == null ? null : new String(bytes, charset));
	}

	public byte[] serialize(String string) {
		return (string == null ? null : string.getBytes(charset));
	}

    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        boolean flag = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            shardedJedis.set(key, value);
            flag=true;
        } catch (Exception e) {
        	LOG.error("set error", e);
        } finally {
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }
        return flag;
    }

    /**
     * 存储object的list
     * @param key
     * @param list
     * @return
     */
    public boolean setObjectList(String key, List<?> list) {
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        boolean flag = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            for (int i = 0; i < list.size(); i++) {
                shardedJedis.lpush(key.getBytes("UTF-8"),SerializationUtils.serialize((Serializable)list.get(i)));
            }
            flag=true;
        } catch (Exception e) {
            LOG.error("setObjectList  error", e);
        } finally {
            if(shardedJedis!=null){
                shardedJedis.close();
            }
        }
        return flag;
    }

    /**
     * 获取object的list
     *
     * @param key
     * @return
     */
    public List<Object> getObjectList(String key) {
        List<byte[]> byteResult;
        List<Object> result = Lists.newArrayList();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if (shardedJedis == null) {
            return result;
        }
        try {
            byteResult = shardedJedis.lrange(key.getBytes("UTF-8"),0,-1);
            if(CollectionUtils.isNotEmpty(byteResult)){
                for (int i = 0; i < byteResult.size(); i++) {
                    Object obj = SerializationUtils.deserialize(byteResult.get(i));
                        result.add(obj);
                }
            }
        } catch (Exception e) {
            LOG.error("redis get error", e);
        } finally {
            if(shardedJedis!=null){
                shardedJedis.close();
            }
        }
        return result;
    }
    /**
     * 批量设置设置
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean setList(String key, String value) {
        boolean flag = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
        	ShardedJedisPipeline shardedJedisPipeline=shardedJedis.pipelined();
        	shardedJedisPipeline.set(key, value);
        	shardedJedisPipeline.sync();
        	flag=true;
        } catch (Exception e) {
        	LOG.error("set error", e);
        } finally {
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }
        return flag;
    }
    /**
     * 设置单个值，并且设置过期时间
     * 
     * @param key
     * @param value
     * @param second
     * @return
     */
    public boolean set(String key, String value, int second) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.setex(key, second, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("set error.", ex);                
        } finally {
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
            
        }  
        return false;  
    }
    /**
     * 删除所有匹配的key
     * 
     * @param prefixKey
     * @return
     */
    public boolean delKesByPrefix(String prefixKey) {
    	ShardedJedis jedis = null;
        Set<String> setResult = new HashSet<>();
        try {
            jedis  = shardedJedisPool.getResource();
            Iterator<Jedis> jedisIterator = jedis.getAllShards().iterator();
            while(jedisIterator.hasNext()){
                setResult = jedisIterator.next().keys(prefixKey+"*");
            }
            Iterator<String> it=setResult.iterator();
            while(it.hasNext()){
            	String key=it.next();
            	jedis.del(key);
            }
            return true; 
        } catch (Exception e) {
        	LOG.error("getByPrefix error", e);
        }finally {
        	if(jedis!=null){
        		jedis.close();
        	}        	  
        }
        return false;
    }
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.get(key);
        } catch (Exception e) {
        	LOG.error("redis get error", e);
        } finally {
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }
        return result;
    }
    /**
     * 删除HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @return 删除的记录数  
     */
    public long del(String key) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.del(key);  
        } catch (Exception ex) {  
            LOG.error("hdel error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return count;  
    }
    /**
     * 设置一个key的过期时间（单位：秒）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            多少秒后过期  
     * @return 1：设置了过期时间, 0：没有设置过期时间/不能设置过期时间  
     */
    public long expire(String key, int seconds) {  
        if (key == null || "".equals(key)) {  
            return 0;  
        }    
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.expire(key, seconds);  
        } catch (Exception ex) {  
        	LOG.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);   
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }  
        return 0;  
    } 
    /**
     * 设置HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @param value  
     *            Json String or String value  
     * @return  
     */
    public boolean hset( String key,String field, String value) {  
        if (value == null) {
            return false;
        }
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.hset(key, field, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("hset error.", ex);  
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }  
        return false;  
    }  
    /**
     * 设置HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @param value  
     *            Json String or String value  
     * @return  
     */
    public boolean hmset(String key, Map<String ,String> map ) {  
        if (map == null) {
            return false;
        }
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.hmset(key, map);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("hmset error.", ex);  
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return false;  
    }
    /**
     * 设置HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @param value  
     *            Json String or String value  
     * @return  
     */
    public boolean hmsetObject(String key, Map<String ,Object> map ) {  
        if (map == null) {
            return false;
        }
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            final Map<byte[], byte[]> hashes = new LinkedHashMap<>(map.size());

    		for (Map.Entry<String, Object> entry : map.entrySet()) {
    			hashes.put(serialize(entry.getKey()), SerializeUtil.serialize(entry.getValue()));
    		}
            shardedJedis.hmset(serialize(key), hashes);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("hmset error.", ex);  
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return false;  
    }
    
    /**
     * 设置HashSet对象  
     *  
     * @param key  
     *            键值
     * @param map
     *            Json String or String value  
     * @return  
     */
    public <T> boolean hmsetObjectClass(String key, Map<String ,T> map) {  
        if (map == null) {
            return false;
        }
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            final Map<byte[], byte[]> hashes = new LinkedHashMap<>(map.size());

    		for (Map.Entry<String, T> entry : map.entrySet()) {
    			hashes.put(serialize(entry.getKey()), SerializeUtil.serialize(entry.getValue()));
    		}
            shardedJedis.hmset(serialize(key), hashes);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("hmset error.", ex);  
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return false;  
    }
    
    /**
     * 设置HashSet对象 ，并且设置过期时间 
     *  
     * @param key  
     *            键值  
     * @param map  
     *              
     * @param seconds  
     *            过期时间，单位秒。               
     * @return  
     */
    public boolean hmset(String key, Map<String ,String> map,int seconds ) { 
    	if(this.hmset(key, map)){
    		return this.expire(key, seconds)==1;
    	}    	          
        return false;  
    }
  
    /**
     * 获得HashSet对象  
     *   
     * @param key  
     *            键值  
     * @param field  
     *            属性             
     * @return Json String or String value  
     */
    public String hget(String key, String field) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.hget(key, field);  
        } catch (Exception ex) {  
            LOG.error("hget error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return null;  
    }
    /**
     * 获得HashSet对象  
     *   
     * @param key  
     *            键值  
     * @param field  
     *            属性             
     * @return Json String or String value  
     */
    public Object hgetObject(String key, String field) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource(); 
            byte[] value=shardedJedis.hget(serialize(key), serialize(field));
            return SerializeUtil.deserialize(value);  
        } catch (Exception ex) {  
            LOG.error("hget error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }  
        return null;  
    }
    /**
     * 获得HashSet对象  
     *  
     * @param key  
     *            键值  
     * @return Json String or String value  
     */
    public Map<String,Object> hgetMap(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            Map<byte[], byte[]> entries =shardedJedis.hgetAll(serialize(key));
            Map<String,Object> map = new LinkedHashMap<>(entries.size());

    		for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
    			map.put(deserialize(entry.getKey()), SerializeUtil.deserialize(entry.getValue()));
    		}
            return map;
        } catch (Exception ex) {  
            LOG.error("hgetAll error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return null;  
    }
  
    /**
     * 获得缓存的Map,指定class
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
	public <T> Map<String, T> hgetMapClass(Class<T> t,String key) {
    	 ShardedJedis shardedJedis = null;  
         try {  
             shardedJedis = shardedJedisPool.getResource();  
             Map<byte[], byte[]> entries =shardedJedis.hgetAll(serialize(key));
             Map map = new LinkedHashMap<String,Object>(entries.size());

     		for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
     			map.put(deserialize(entry.getKey()), t.cast(SerializeUtil.deserialize(entry.getValue())));
     		}
             return map;
         } catch (Exception ex) {  
             LOG.error("hgetAll error.", ex);
         } finally {  
         	if(shardedJedis!=null){
         		shardedJedis.close(); 
         	} 
         }
		return null;  
    }
    /**
     * 删除HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性值  
     * @return 删除的记录数  
     */
    public long hdel(String key,String field) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.hdel(key,field);  
        } catch (Exception ex) {  
            LOG.error("hdel error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return count;  
    }  
  
    /**
     * 删除HashSet对象  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @return 删除的记录数  
     */
    public long hdel(String key, String... field) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.hdel(key,field);  
        } catch (Exception ex) {  
            LOG.error("hdel error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return count;  
    }  
  
    /**
     * 判断key是否存在  
     *  
     * @param key  
     *            键值  
     * @param field  
     *            属性  
     * @return  
     */
    public boolean hexists(String key, String field) {  
        ShardedJedis shardedJedis = null;  
        boolean isExist = false;  
        try {  
            shardedJedis = shardedJedisPool.getResource(); 
            isExist = shardedJedis.hexists(key,field);  
        } catch (Exception ex) {  
            LOG.error("hexists error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return isExist;  
    }    
    
    /**
     * 判断key是否存在  
     *  
     * @param key  
     *            键值  
     * @return  
     */
    public boolean exists(String key) {  
        ShardedJedis shardedJedis = null;  
        boolean isExist = false;  
        try {  
            shardedJedis = shardedJedisPool.getResource(); 
            isExist = shardedJedis.exists(key); 
        } catch (Exception ex) {  
            LOG.error("hexists error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return isExist;  
    }
  
    /**
     * 返回 key 指定的哈希集中所有字段的value值  
     *  
     * @param key  
     * @return  
     */
  
    public List<String> hvals(String key) {  
        ShardedJedis shardedJedis = null;  
        List<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hvals(key);  
        } catch (Exception ex) {  
            LOG.error("hvals error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return retList;  
    }
    /**
     * 返回 key 指定的哈希集中所有字段的value值  
     *  
     * @param key  
     * @return  
     */
  
    public <T> List<T> hvalsObject(String key) {  
        ShardedJedis shardedJedis = null;  
        List<Object> retList = new ArrayList<>();  
        try {  
            shardedJedis = shardedJedisPool.getResource();
            Collection<byte[]> byteList=shardedJedis.hvals(serialize(key));
            Iterator<byte[]> it=byteList.iterator();
            while(it.hasNext()){
            	retList.add(SerializeUtil.deserialize(it.next()));
            } 
        } catch (Exception ex) {  
            LOG.error("hvals error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }  
        return (List<T>)retList;  
    } 
  
    /**
     * 返回 key 指定的哈希集中所有字段的field值  
     *  
     * @param key  
     * @return  
     */
  
    public Set<String> hkeys(String key) {  
        ShardedJedis shardedJedis = null;  
        Set<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hkeys(key);  
        } catch (Exception ex) {  
            LOG.error("hkeys error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return retList;  
    }  
  
    /**
     * 返回 key 指定的哈希field值总数  
     *  
     * @param key  
     * @return  
     */
    public long hlen(String key) {  
        ShardedJedis shardedJedis = null;  
        long retList = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hlen(key);  
        } catch (Exception ex) {  
            LOG.error("hkeys error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return retList;  
    }  
  
    /**
     * 设置排序集合  
     *  
     * @param key  
     * @param score  
     * @param value  
     * @return  
     */
    public boolean setSortedSet(String key, long score, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.zadd(key, score, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("setSortedSet error.", ex);  
             
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return false;  
    }
     
  
    /**
     * 获得排序集合  
     *  
     * @param key  
     * @param startScore  
     * @param endScore  
     * @param orderByDesc  
     * @return  
     */
    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
                return shardedJedis.zrevrangeByScore(key, endScore, startScore);  
            } else {  
                return shardedJedis.zrangeByScore(key, startScore, endScore);  
            }  
        } catch (Exception ex) {  
        	LOG.error("getSoredSet error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return Collections.emptySet();  
    }  
  
    /**
     * 计算排序长度  
     *  
     * @param key  
     * @param startScore  
     * @param endScore  
     * @return  
     */
    public long zcount(String key, long startScore, long endScore) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            Long count = shardedJedis.zcount(key, startScore, endScore);  
            return count == null ? 0L : count;  
        } catch (Exception ex) {  
            LOG.error("zcount error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}
        }  
        return 0L;  
    }  
  
    /**
     * 删除排序集合  
     *  
     * @param key  
     * @param value  
     * @return  
     */
    public boolean zrem(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            long count = shardedJedis.zrem(key, value);  
            return count > 0;  
        } catch (Exception ex) {  
            LOG.error("zrem error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return false;  
    }  
  
    /**
     * 获得排序集合  
     *  
     * @param key  
     * @param startRange  
     * @param endRange  
     * @param orderByDesc  
     * @return  
     */
    public Set<String> zrange(String key, int startRange, int endRange, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
                return shardedJedis.zrevrange(key, startRange, endRange);  
            } else {  
                return shardedJedis.zrange(key, startRange, endRange);  
            }  
        } catch (Exception ex) {  
            LOG.error("zrange error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return Collections.emptySet();  
    }  
  
    /**
     * 获得排序打分  
     *  
     * @param key  
     * @return  
     */
    public Double zscore(String key, String member) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.zscore(key, member);  
        } catch (Exception ex) {  
            LOG.error("zscore error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	} 
        }  
        return null;  
    }
    /**
     * 从list中删除value 默认count 1  
     *   
     * @param key  
     * @param values  
     *            值list  
     * @return  
     */
    public int lrem(String key, List<String> values) {  
        return lrem(key, 1, values);  
    }  
  
    /**
     * 从list中删除value  
     *   
     * @param key  
     * @param count  
     * @param values  
     *            值list  
     * @return  
     */
    public int lrem(String key, long count, List<String> values) {  
        int result = 0;  
        if (values != null && !values.isEmpty()) {  
            for (String value : values) {  
                if (lrem(key, count, value)) {  
                    result++;  
                }  
            }  
        }  
        return result;  
    }  
  
    /**
     * 从list中删除value  
     *   
     * @param key  
     * @param count  
     *            要删除个数  
     * @param value  
     * @return  
     */
    public boolean lrem(String key, long count, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.lrem(key, count, value);  
            return true;  
        } catch (Exception ex) {  
        	LOG.error("lrem error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return false;  
    }  
  
    /**
     * 截取List  
     *   
     * @param key  
     * @param start  
     *            起始位置  
     * @param end  
     *            结束位置  
     * @return  
     */
    public List<String> lrange(String key, long start, long end) {  
        if (key == null || "".equals(key)) {  
            return null;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.lrange(key, start, end);  
        } catch (Exception ex) {  
            LOG.error("lrange 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}   
        }  
        return null;  
    }  
  
    /**
     * 检查List长度  
     *   
     * @param key  
     * @return  
     */
    public long llen(String key) {  
        if (key == null) {  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.llen(key);  
        } catch (Exception ex) {  
            LOG.error("llen error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return 0;  
    }  
  
    /**
     * 添加到List中（同时设置过期时间）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            过期时间 单位s  
     * @param value  
     * @return  
     */
    public boolean lpush(String key, int seconds, String... value) {  
        boolean result = lpush(key, value);  
        if (result) {  
            long i = expire(key, seconds);  
            return i == 1;  
        }  
        return false;  
    }  
  
    /**
     * 添加到List  
     *   
     * @param key  
     * @param value  
     * @return  
     */
    public boolean lpush(String key, String... value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.lpush(key, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("lpush error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}   
        }  
        return false;  
    }  
  
    /**
     * 添加到List(只新增)  
     *   
     * @param key  
     * @param list
     * @return  
     */
    public boolean lpush(String key, List<String> list) {  
        if (key == null || list == null || list.size() == 0) {  
            return false;  
        }  
        for (String value : list) {  
        	lpush(key, value);  
        }  
        return true;  
    }
    /**
     * 截断一个List  
     *   
     * @param key  
     *            列表key  
     * @param start  
     *            开始位置 从0开始  
     * @param end  
     *            结束位置  
     * @return 状态码  
     */
    public String ltrim(String key, long start, long end) {  
        if (key == null || "".equals(key)) {
            return "-";  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.ltrim(key, start, end);  
        } catch (Exception ex) {  
            LOG.error("ltrim 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return "-";  
    }  
  
    /**
     * 检查Set长度  
     *   
     * @param key  
     * @return  
     */
    public long scard(String key) {  
        if (key == null) {  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.scard(key);  
        } catch (Exception ex) {  
            LOG.error("scard error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return 0;  
    }  
  
    /**
     * 添加到Set中（同时设置过期时间）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            过期时间 单位s  
     * @param value  
     * @return 成功true  
     */
    public boolean sadd(String key, int seconds, String... value) {  
        boolean result = sadd(key, value);  
        if (result) {  
            long i = expire(key, seconds);  
            return i == 1;  
        }  
        return false;  
    }  
  
    /**
     * 添加到Set中  
     *   
     * @param key  
     * @param value  
     * @return  
     */
    public boolean sadd(String key, String... value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.sadd(key, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("setList error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return false;  
    }
    /**
     * 添加到Set中  
     *   
     * @param key  
     * @param values
     * @return  
     */
    public boolean saddObject(String key, Object... values) {  
        if (key == null || values == null) {  
            return false;  
        }
        int length=values.length;
        byte[][] array=new byte[length][];
        
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource(); 
            int i = 0;
            for(Object value : values){
            	array[i++]=SerializeUtil.serialize(value);
            }            
            shardedJedis.sadd(serialize(key), array);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("setList error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return false;  
    }
    
  
    /**
     * @param key  
     * @param value  
     * @return 判断值是否包含在set中  
     */
    public boolean sismember(String key, String value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.sismember(key, value);  
        } catch (Exception ex) {  
            LOG.error("sismember error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return false;  
    }  
  
    /**
     * 获取Set  
     *   
     * @param key  
     * @return  
     */
    public Set<String> smembers(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.smembers(key);  
        } catch (Exception ex) {  
            LOG.error("smembers error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return null;  
    }
    /**
     * 获取Set  
     *   
     * @param key  
     * @return  
     */
	public List<Object> smembersObjct(String key) {
		List<Object> list = new ArrayList<>();
		Set<byte[]> set = smembersByte(key);
		Iterator<byte[]> it = set.iterator();
		while (it.hasNext()) {
			list.add(SerializeUtil.deserialize(it.next()));
		}
		return list;

	}
    /**
     * 获取Set  
     *   
     * @param key  
     * @return  
     */
    public Set<byte[]> smembersByte(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.smembers(key.getBytes(charset));  
        } catch (Exception ex) {  
            LOG.error("smembers error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return null;  
    }
  
    /**
     * 从set中删除value  
     *   
     * @param key  
     * @return  
     */
    public boolean srem(String key, String... value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.srem(key, value);  
            return true;  
        } catch (Exception ex) {  
            LOG.error("getList error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}  
        }  
        return false;  
    }  
  
    /**
     * 获取List  
     *   
     * @param key  
     * @return  
     */
    public List<String> lrange(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.lrange(key, 0, -1);  
        } catch (Exception ex) {  
            LOG.error("lrange error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}   
        }  
        return null;  
    }
    /**
     * 获取List  
     *   
     * @param key  
     * @return  
     */
    public List<Object> lrangeObject(String key) {
    	List<Object> objList=new ArrayList<>();
    	List<byte[]> list=lrangeByte( key) ;
    	if(list !=null &&!list.isEmpty()){
    		for (int i=0;i<list.size();i++){
    			objList.add(SerializeUtil.deserialize(list.get(i)));
    		}
    	}
    	return objList;
    }
    /**
     * 获取List  
     *   
     * @param key  
     * @return  
     */
    public List<byte[]> lrangeByte(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
             
            return shardedJedis.lrange(key.getBytes(charset), 0, -1);
            
            
        } catch (Exception ex) {  
            LOG.error("lrange error.", ex);  
              
        } finally {  
        	if(shardedJedis!=null){
        		shardedJedis.close(); 
        	}   
        }  
        return null;  
    }
    
    public Object getValue(String key) {  
        Object ret = null;  
        ShardedJedis jedis = shardedJedisPool.getResource();  
        try {  
  
            // 去redis中取回序列化后的对象  
            byte[] obj = jedis.get(serialize(key));  
  
            // 取回的对象反序列化  
            if (obj != null) {  
                ret = SerializeUtil.deserialize(obj);  
            }    
        } catch (Exception e) {
        	LOG.error("get error.", e); 
        } finally {
        	if(jedis!=null){
        		jedis.close(); 
        	}  
        }  
        return ret;  
    }      
}
