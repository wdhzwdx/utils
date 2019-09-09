package com.JUC;

import java.util.ArrayList;
import java.util.List;


/**
 * cas 原子性
 * @author wdh
 *
 */
public class AtomicIntegerDemo2 {

  private static int data = 0;
   static class Test1 implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				
			}
			System.out.print(data+++",");
		}
	}
	
	public static void main(String[] args) {
		List<Test1> datas = new ArrayList<>();
		for(int i=0;i<10000;i++){
			Test1 test1 = new Test1();
			datas.add(test1);
		}
		for(Test1 item :datas){
			new Thread(item).start();
		}
	}
	
}
