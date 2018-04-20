package com.example.other.thread.concurrentTool.countDown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class xx2 {
	
	
	private void add() throws InterruptedException {
		// TODO Auto-generated method stub
		
		final CountDownLatch begin = new CountDownLatch(1);
		final CountDownLatch count = new CountDownLatch(10);
		ExecutorService service = Executors.newFixedThreadPool(5);
		
		for (int i = 0; i < 10; i++) {
			final int no = i+1;
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						begin.await();
						Thread.sleep((long) (Math.random() * 10000));  
						System.out.println(no);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						count.countDown();
					}
					
				}
			};
			service.submit(run);
		}
		begin.countDown();
		System.out.println("start");
		count.await();
		System.out.println("end");
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new xx2().add();
	}
}
