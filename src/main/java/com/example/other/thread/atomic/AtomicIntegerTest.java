package com.example.other.thread.atomic;
import java.util.concurrent.atomic.AtomicInteger;

/**原子更新基本类型类：int类型
 * @author binzhao
 * @date 2017年3月15日
 */
public class AtomicIntegerTest {

	static AtomicInteger ai = new AtomicInteger(1);

	public static void main(String[] args) {
		//返回的是新值（即加1后的值）
		System.out.println(ai.incrementAndGet());
		//方法是返回旧值（即加1前的原始值）
		System.out.println(ai.getAndIncrement());
		//获取当前值。
		System.out.println(ai.get());
	}

}


