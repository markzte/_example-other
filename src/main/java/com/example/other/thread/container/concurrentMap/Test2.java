package com.example.other.thread.container.concurrentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**Test2:统计文本中单词出现的次数，把单词出现的次数记录到一个Map中
 * @author binzhao
 * @date 2017年4月17日
 */
public class Test2 {
	private final ConcurrentMap<String, Long> wordCounts = new ConcurrentHashMap<String, Long>();
	
	public long increase(String word) {
	    Long oldValue, newValue;
	    while (true) {
	        oldValue = wordCounts.get(word);
	        if (oldValue == null) {
	            // Add the word firstly, initial the value as 1
	            newValue = 1L;
	            if (wordCounts.putIfAbsent(word, newValue) == null) {
	                break;
	            }
	        } else {
	            newValue = oldValue + 1;
	            if (wordCounts.replace(word, oldValue, newValue)) {
	                break;
	            }
	        }
	    }
	    return newValue;
	}
}

