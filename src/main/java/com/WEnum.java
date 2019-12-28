package com;

public enum WEnum {
    w1("w1"),
    w2("w2");
    private String name;
    private WEnum(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
