package com.JUC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cas 原子性
 * @author wdh
 *
 */
public class AtomicIntegerDemo {
	private static AtomicInteger number = new AtomicInteger();

	static class Test1 implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				
			}
			System.out.print(add()+",");
		}
		
		public int add(){
			return number.getAndIncrement();
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
