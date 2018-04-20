package com.example.other.thread;

/**生产者与消费者
 * @author binzhao
 * @date 2017年1月12日
 */
public class ProducerCustomer {
	public static void main(String[] args) {
		Q q = new Q();
		Producer p = new Producer(q);
		Customer c = new Customer(q);
		p.start();
		c.start();
	}
}

class Producer extends Thread {
	Q q;

	public Producer(Q q) {
		this.q = q;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			q.put(i);
			System.out.println("producer:put " + i);
		}
	}
}

class Customer extends Thread {
	Q q;

	public Customer(Q q) {
		this.q = q;
	}

	public void run() {
		while (true) {
			System.out.println("Customer :get " + q.get());
		}
	}
}

class Q {
	private int value;
	boolean isExist = false;		//是否还有商品

	//生产者
	public synchronized void put(int i) {
		if (!isExist) {	//没有产品
			value = i;		//生产产品
			isExist = true;
			notify();	//提醒消费者有产品可以消费
		}
		try {
			wait();	//释放对象锁，暂时不再生产，等待消费
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//消费者
	public synchronized int get() {
		if (!isExist) {	//没有产品
			try {
				wait();	//释放对象锁，等待生产
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		notify();	//唤醒生产者，提醒需要生产产品
		isExist = false;	
		return this.value;
	}
}