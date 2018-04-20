package com.example.other.thread.lock.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**重入锁：也是排它锁，一般情况下，排它锁性能比读写锁低
 * Java的等待通知机制
 * @author binzhao
 * @date 2017年4月17日
 */
public class ReentrantLockTest {

	/**
	 * 获取一个Condition必须通过Lock的newCondition()方法。下面通过一个有界队列的示例来
	 * 深入了解Condition的使用方式。有界队列是一种特殊的队列，当队列为空时，队列的获取操作
	 * 将会阻塞获取线程，直到队列中有新增元素，当队列已满时，队列的插入操作将会阻塞插入线
	 * 程，直到队列出现“空位”，
	 */
	static class BoundedQueue<T> {
		//数组
		private Object[] items;
		// 添加的下标，删除的下标和数组当前数量
		private int addIndex, removeIndex, count;
		//默认非公平锁，不过提供了个公平锁参数控制：new ReentrantLock(true)
		private Lock lock = new ReentrantLock();
		private Condition notEmpty = lock.newCondition();
		private Condition notFull = lock.newCondition();

		public BoundedQueue(int size) {
			items = new Object[size];
		}

		// 添加一个元素，如果数组满，则添加线程进入等待状态，直到有"空位"
		public void add(T t) throws InterruptedException {
			System .out.println("add: wait lock");
			lock.lock();
			System.out.println("add: get lock");
			try {
				while (count == items.length){
					System.out.println("buffer full, please wait");
					notFull.await();
				}
				items[addIndex] = t;
				if (++addIndex == items.length)
					addIndex = 0;
				++count;
				System.out.println("已添加："+t);
				System.out.println("同时通知等待在notEmpty上的线程，数组中已经有新元素可以获取。");
				notEmpty.signal();
			} finally {
				lock.unlock();
			}
		}

		// 由头部删除一个元素，如果数组空，则删除线程进入等待状态，直到有新添加元素
		@SuppressWarnings("unchecked")
		public T remove() throws InterruptedException {
			System.out.println("remove: wait lock");
			lock.lock();
			System.out.println("remove: get lock");
			try {
				while (count == 0){
					System.out.println("no elements, please wait");
					notEmpty.await();
				}
				Object x = items[removeIndex];
				if (++removeIndex == items.length)
					removeIndex = 0;
				--count;
				System.out.println("取："+x);
				System.out.println("同时通知等待在notFull上的线程，数组中没有元素了");
				notFull.signal();  
				return (T) x;
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		final  BoundedQueue<Integer>  boundedBuffer = new ReentrantLockTest.BoundedQueue<Integer>(10);

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("t1 run");
				for (int i = 0; i < 100; i++) {
					try {
						System.out.println("adding..");
						boundedBuffer.add(Integer.valueOf(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						Object val = boundedBuffer.remove();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		t1.start();
		t2.start();
	}
}
