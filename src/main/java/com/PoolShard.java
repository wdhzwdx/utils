package com;

public class PoolShard {

    //分表总数
    private static final int tableCount = 2<<6;

    public static void getPersonIndexTableName(String userId){
        int hash = Math.abs(userId.hashCode())%tableCount+1;
        System.out.println("个人归集索引表名后缀："+hash);
    }

    public static void getCorpIndexTableName(String taxnum){
        int hash = Math.abs(taxnum.hashCode())%tableCount+1;
        System.out.println("企业归集索引表名后缀："+hash);
    }

    public static void getFpdmFphmTableName(String fpdm, String fphm){
        int hash = Math.abs(fpdm.hashCode()+fphm.hashCode())%tableCount+1;
        System.out.println("发票代码+发票号码分表后的表名后缀："+hash);
    }

    public static void main(String[] args) {
        getPersonIndexTableName("C56F3AAACD4A4B518A06482A204C20EC");
        getCorpIndexTableName("339901999999142");
        getFpdmFphmTableName("3300173320","05671536");
    }

}
