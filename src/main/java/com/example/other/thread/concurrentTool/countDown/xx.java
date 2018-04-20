package com.example.other.thread.concurrentTool.countDown;

import java.util.concurrent.CountDownLatch;

public class xx {
	
	CountDownLatch count = new CountDownLatch(1);
	
	private void add() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("start");
		count.countDown();
		Thread.sleep(3000);
		count.await();
		System.out.println("end");
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new xx().add();
	}
}
