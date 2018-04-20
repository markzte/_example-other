package com.example.other.thread;

/**设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。写出程序。
 * @author binzhao
 * @date 2017年1月12日
 */
public class AddNumberOne {
	private int j;

	public static void main(String args[]) {
		AddNumberOne tt = new AddNumberOne();
		Inc inc = tt.new Inc();
		Dec dec = tt.new Dec();
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(inc);
			t.start();
			t = new Thread(dec);
			t.start();
		}
	}

	private synchronized void inc() {
		j++;
		System.out.println(Thread.currentThread().getName() + "-inc:" + j);
	}

	private synchronized void dec() {
		j--;
		System.out.println(Thread.currentThread().getName() + "-dec:" + j);
	}

	//加1线程
	class Inc implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				inc();
			}
		}
	}
	//减1线程
	class Dec implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				dec();
			}
		}
	}
}
