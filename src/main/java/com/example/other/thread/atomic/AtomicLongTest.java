package com.example.other.thread.atomic;
import java.util.concurrent.atomic.AtomicLong;


/**原子更新基本类型类：long类型
 * @author binzhao
 * @date 2017年3月15日
 */
public class AtomicLongTest {
	private static AtomicLong counter = new AtomicLong(0);

	public static long addOne() {
		return counter.incrementAndGet();
	}
}