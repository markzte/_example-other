package com.example.other.thread.lock.reentrantLock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**读写锁：操作map
 * 一般情况下，读写锁的性能都会比排它锁好，因为大多数场景读是多于写的。在读多于写
 * 的情况下，读写锁能够提供比排它锁更好的并发性和吞吐量。Java并发包提供读写锁的实现是
 * ReentrantReadWriteLock
 * @author binzhao
 * @date 2017年4月17日
 */
public class ReadWriteLockMapTest {
	public static void main(String[] args) {
		final Cache q3 = new Cache();
		for (int i = 0; i < 20; i++) {
			final int ii = i;
			new Thread() {
				public void run() {
					while (true) {
						Object data = q3.get("key_1");
						System.out.println("key_1:"+data);
					}
				}

			}.start();
		}
		for (int i = 0; i < 3; i++) {
			final int ii = i;
			new Thread() {
				public void run() {
					while (true) {
						q3.put("key_1",new Random().nextInt(10000));
					}
				}

			}.start();
		}
	}
}

class Cache {
	static Map<String, Object> map = new HashMap<String, Object>();
	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock r = rwl.readLock();
	static Lock w = rwl.writeLock();

	// 获取一个key对应的value
	public static final Object get(String key) {
		r.lock();
		System.out.println("read data ing"+map.size());
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}

	// 设置key对应的value，并返回旧的value
	public static final Object put(String key, Object value) {
		w.lock();
		map.clear();
		System.out.println("清除缓存");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}

	// 清空所有的内容
	public static final void clear() {
		w.lock();
		try {
			map.clear();
		} finally {
			w.unlock();
		}
	}
}