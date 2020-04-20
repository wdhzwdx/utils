package com.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTests {
    public static void main(String[] args) {
//        testNoBuild();
        testBuild();
    }

    public static void testNoBuild(){

        GirlFriend girlFriend = new GirlFriend();
        girlFriend.setAge(1);
        girlFriend.setBust(2);
        girlFriend.setName("张三");

        Map<String,String> map = new HashMap<>();
        map.put("key","value");
        girlFriend.setGift(map);

        List<String> list = new ArrayList<>();
        list.add("1");
        girlFriend.setHobby(list);

    }

    public static void testBuild(){
        Map<String,String> map = new HashMap<>();
        map.put("我是key","me is value");

        List<String> list = new ArrayList<>();
        list.add("girlFriend1号");
        list.add("girlFriend2号");
        GirlFriend girlFriend = Build.of(GirlFriend::new)
                .with(GirlFriend::setName,"张三")
                .with(GirlFriend::setAge,18)
                .with(GirlFriend::setGift,map)
                .with(GirlFriend::setHobby,list)
                .with(GirlFriend::setBatch1,"batch1_1","batch1_2","batch1_3")
                .with(GirlFriend::setPAge,"p18")
                .with(GirlFriend::setB1,"b1")
                .with(GirlFriend::setB2,"b2")
                .with(GirlFriend::setB3,"b3")
                .with(GirlFriend::setAge,3)
                .with(GirlFriend::setBatch,"批量1_1","批量1_2")
                .build();
        System.out.println(girlFriend);
        
    }
    
}
