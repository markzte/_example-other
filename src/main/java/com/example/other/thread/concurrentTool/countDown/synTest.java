package com.example.other.thread.concurrentTool.countDown;

public class synTest {
		public static int i ;
		
		public static synchronized void update(){
			System.out.println("begin");
			try {
				synTest.i = 2;
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("end");
		}
		
		public static void main(String[] args) {
			System.out.println(synTest.i);
			synTest.update();
			System.out.println(synTest.i);
		}

}
