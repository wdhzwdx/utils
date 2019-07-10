package com.util.serialize;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

/**
 * 通用序列化工具
 * @author wdh
 *
 */
public class SerializeUtil {  
  
    private static Logger LOG = LoggerFactory.getLogger(SerializeUtil.class);  
    /**  
     * 序列化
     *  
     * @param object  
     * @return byte[]
     */
    public static byte[] serialize(Object object) {  
        ObjectOutputStream oos = null;  
        ByteArrayOutputStream baos = null;
        if(object==null){
    		return null;
    	}
        try {  
            // 序列化  
            baos = new ByteArrayOutputStream();  
            oos = new ObjectOutputStream(baos);  
            oos.writeObject(object);  
            byte[] bytes = baos.toByteArray();  
            return bytes;  
        } catch (Exception e) {
        	LOG.error("serialize error",e); 
        } finally {  
            try {  
                if (oos != null) {  
                    oos.close();  
                }  
                if (baos != null) {  
                    baos.close();  
                }  
            } catch (Exception e2) {
            	LOG.error("serialize close error",e2); 
            }  
        }  
        return null;  
    }  
    /**  
     * 序列化
     *  
     * @param byte[]  
     * @return object
     */
    public static Object deserialize(byte[] bytes) { 
    	if(bytes==null){
    		return null;
    	}
        ByteArrayInputStream bais = null;  
        try {  
            // 反序列化  
            bais = new ByteArrayInputStream(bytes);  
            ObjectInputStream ois = new ObjectInputStream(bais);  
            return ois.readObject();  
        } catch (Exception e) { 
        	LOG.error("unserialize error",e); 
        }  
        return null;  
    }  
  
} 
