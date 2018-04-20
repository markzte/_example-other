package com.example.other.thread.other.threadpoolTest;
import java.util.concurrent.ArrayBlockingQueue;  
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;  
import java.util.concurrent.TimeUnit;  
  
public class TestThreadPool {  
  
    private static int produceTaskSleepTime = 2;  
    private static int produceTaskMaxNumber = 10;  
  
    public static void main(String[] args) {  
    	ThreadFactory threadFactory = Executors.defaultThreadFactory();
    	 
        // 构造一个线程池  
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),threadFactory);  
  
        for (int i = 1; i <= produceTaskMaxNumber; i++) {  
            try {  
                String task = "task@ " + i;  
                System.out.println("创建任务并提交到线程池中：" + task);  
                threadPool.execute(new ThreadPoolTask(task));  
                Thread.sleep(produceTaskSleepTime);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  