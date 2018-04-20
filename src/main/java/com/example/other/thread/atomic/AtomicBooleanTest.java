package com.example.other.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**原子更新基本类型类：boolean类型
 * @author binzhao
 * @date 2017年3月15日
 */
public class AtomicBooleanTest implements Runnable {

	private static AtomicBoolean exists = new AtomicBoolean(false);
	private String name;

	public AtomicBooleanTest(String name) {
		this.name = name;
	}

	public void run() {
		if (exists.compareAndSet(false, true)) {
			System.out.println(name + " enter");
			try {
				System.out.println(name + " working");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// do nothing
			}
			System.out.println(name + " leave");
			exists.set(false);
		} else {
			System.out.println(name + " give up");
		}
	}

}
