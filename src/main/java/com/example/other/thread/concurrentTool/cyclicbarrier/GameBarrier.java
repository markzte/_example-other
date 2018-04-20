package com.example.other.thread.concurrentTool.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**有四个游戏玩家玩游戏，游戏有三个关卡，每个关卡必须要所有玩家都到达后才能允许通关。
 * 其 实这个场景里的玩家中如果有玩家A先到了关卡1，他必须等待其他所有玩家都到达关卡1时才能通过，也就是说线程之间需要互相等待，
 * 这和 CountDownLatch的应用场景有区别，CountDownLatch里的线程是到了运行的目标后继续干自己的其他事情，
 * 而这里的线程需要等待其 他线程后才能继续完成下面的工作。
 * @author binzhao
 * @date 2017年5月4日
 */
public class GameBarrier {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
			@Override
			public void run() {
				System.out.println("===========所有玩家进入第二关！");
			}
		});

		for (int i = 0; i < 4; i++) {
			new Thread(new Player(i, cyclicBarrier)).start();
		}
	}
}

class Player implements Runnable {
	private CyclicBarrier cyclicBarrier;
	private int id;

	public Player(int id, CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
		this.id = id;
	}

	@Override
	public void run() {
		try {
			System.out.println("玩家" + id + "正在玩第一关...");
			cyclicBarrier.await();
			System.out.println("玩家" + id + "进入第二关...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}