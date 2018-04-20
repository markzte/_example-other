package com.example.other.thread.container.concurrentMap;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**Test3:统计文本中单词出现的次数，把单词出现的次数记录到一个Map中
 * @author binzhao
 * @date 2017年4月17日
 */
public class Test3 {
	private final ConcurrentMap<String, Future<ExpensiveObj>> cache = new ConcurrentHashMap<String, Future<ExpensiveObj>>();
	
	public ExpensiveObj get(final String key) {
	    Future<ExpensiveObj> future = cache.get(key);
	    if (future == null) {
	        Callable<ExpensiveObj> callable = new Callable<ExpensiveObj>() {
	            @Override
	            public ExpensiveObj call() throws Exception {
	                return new ExpensiveObj(key);
	            }
	        };
	        FutureTask<ExpensiveObj> task = new FutureTask<ExpensiveObj>(callable);
	
	        future = cache.putIfAbsent(key, task);
	        if (future == null) {
	            future = task;
	            task.run();
	        }
	    }
	
	    try {
	        return future.get();
	    } catch (Exception e) {
	        cache.remove(key);
	        throw new RuntimeException(e);
	    }
	}
}

