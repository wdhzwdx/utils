package com.proxy.cglib;


public class CglibTest {

	public static void main(String[] args) {
//              test1();
//              test2();
        test3();
	}

	public static void test1(){
        //	    for (int i =0;i<100;i++)  {
//            new Thread(()->{
        CgI cgI = new CgDao();
        CglibProxy1 proxy = new CglibProxy1();
        cgI = proxy.getProxy(cgI,CgI.class);
        String t = cgI.toInfo("1","jjj");
        System.out.println(t);
//            }).start();
//        }
    }

    public static void test2(){
//	    for (int i =0;i<10;i++)  {
//            new Thread(()->{
                CgI cgI = new CgDao();
                CglibProxy2 proxy = new CglibProxy2();
                cgI = proxy.getProxy(cgI,CgI.class);
                String t = cgI.toInfo("1","jjj");
                System.out.println(t);
//                    }).start();
//        }
    }

    public static void test3(){
//	    for (int i =0;i<10;i++)  {
//            new Thread(()->{
        Cg2 cgI = new CgDao();
        CglibProxy3 proxy = new CglibProxy3();
        cgI = proxy.getProxy(cgI,Cg2.class);
        String t = cgI.toInfo("1","jjj");
        System.out.println(t);
//                    }).start();
//        }
    }

}
