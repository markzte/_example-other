package com.example.other.thread.container.concurrentMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**Test1:统计文本中单词出现的次数，把单词出现的次数记录到一个Map中【非正确示例】
 * 如果多个线程并发调用这个increase()方法，increase()的实现就是错误的，因为多个线程用相同的word调用时，
 * 很可能会覆盖相互的结果，造成记录的次数比实际出现的次数少。
 * @author binzhao
 * @date 2017年4月17日
 */
public class Test1 {
	private final Map<String, Long> wordCounts = new ConcurrentHashMap<String, Long>();
	
	public long increase(String word) {
	    Long oldValue = wordCounts.get(word);
	    Long newValue = (oldValue == null) ? 1L : oldValue + 1;
	    wordCounts.put(word, newValue);
	    return newValue;
	}
}

