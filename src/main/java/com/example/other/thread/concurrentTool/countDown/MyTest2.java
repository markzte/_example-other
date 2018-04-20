package com.example.other.thread.concurrentTool.countDown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTest2 {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("enter");
		Thread.sleep((long) (Math.random()*1000));
//		fdsfsd.countDownLatch.countDown();
		System.out.println("begin");
		
		int size = 10;
		ExecutorService  executor = Executors.newFixedThreadPool(size);
		final CountDownLatch begin = new CountDownLatch(10);
		
		for (int i = 0; i < size; i++) {
			final int num = i+1;
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
//					try {
//						Thread.sleep((long) (Math.random()*10000));
						System.out.println(num+":"+fdsfsd.i);
						begin.countDown();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			};
			executor.submit(run);
		}
		begin.await();
		System.out.println("end");
		
	}
}
class fdsfsd{
	public static CountDownLatch countDownLatch = new CountDownLatch(1);
	 
	public static int i = 0;
	
	static{
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
