package com.example.other.thread.lock.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**重入锁：也是排它锁，
 * @author binzhao
 * @date 2017年4月17日
 */
public class ReentrantLockTest2 {
	
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadExecute(business, "sub");
			}
		}).start();
		threadExecute(business, "main");
	}	
	
	public static void threadExecute(Business business, String threadType) {
		for(int i = 0; i < 100; i++) {
			try {
				if("main".equals(threadType)) {
					business.main(i);
				} else {
					business.sub(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class Business {
	private boolean bool = true;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition(); 
	
	public /*synchronized*/ void main(int loop) throws InterruptedException {
		lock.lock();
		try {
			while(bool) {				
				condition.await();//this.wait();
			}
			for(int i = 0; i < 100; i++) {
				System.out.println("主线程 seq of " + i + ", loop of " + loop);
			}
			bool = true;
			condition.signal();//this.notify();
		} finally {
			lock.unlock();
		}
	}	
	
	public /*synchronized*/ void sub(int loop) throws InterruptedException {
		lock.lock();
		try {
			while(!bool) {
				condition.await();//this.wait();
			}
			for(int i = 0; i < 10; i++) {
				System.out.println("子线程 seq of " + i + ", loop of " + loop);
			}
			bool = false;
			condition.signal();//this.notify();
		} finally {
			lock.unlock();
		}
	}
}