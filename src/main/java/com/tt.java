package com;

public interface tt {
    void te();
    static void te1(){
        System.out.println("接口静态方法");
    }

    default void testDefault(){
        System.out.println("默认接口方法");
    }
}
