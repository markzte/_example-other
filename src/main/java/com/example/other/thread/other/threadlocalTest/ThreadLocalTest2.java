package com.example.other.thread.other.threadlocalTest;

/**ThreadLocal：线程本地变量之---对象引用的副本
 * @author binzhao
 * @date 2017年3月10日
 */
public class ThreadLocalTest2 {
	
	private static Index num = new Index();
        //创建一个Index类型的本地变量 
	private static ThreadLocal<Index> local = new ThreadLocal<Index>() {
		@Override
		protected Index initialValue() {
			return num;
		}
	};

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[5];
		for (int j = 0; j < 5; j++) {
			threads[j] = new Thread(new Runnable() {
				@Override
				public void run() {
                    //取出当前线程的本地变量，并累加1000次
					Index index = local.get();
					for (int i = 0; i < 1000; i++) {                                          
						index.increase();
					}
					System.out.println(Thread.currentThread().getName() + " : "+ index.num);

				}
			}, "Thread-" + j);
		}
		for (Thread thread : threads) {
			thread.start();
		}
	}

	static class Index {
		int num;

		public void increase() {
			num++;
		}
	}
}