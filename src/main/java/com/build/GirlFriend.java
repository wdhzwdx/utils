package com.build;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class GirlFriend extends ParentGirlFriend{

    private String name;

    private int age;

    private int bust;

    private List<String> hobby;

    private Map<String,String> gift;

    private String b1;

    private String b2;

    private String b3;

    public void setBatch(String b1,String b2){
        this.b1 = b1;
        this.b2 = b2;
    }

    public void setBatch1(String b1,String b2,String b3){
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
    }

}
