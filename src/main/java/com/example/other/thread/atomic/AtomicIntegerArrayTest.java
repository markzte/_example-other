package com.example.other.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**原子更新:数组类
 * @author binzhao
 * @date 2017年3月15日
 */
public class AtomicIntegerArrayTest {

	static int[] value = new int[] { 1, 2 };

	static AtomicIntegerArray ai = new AtomicIntegerArray(value);

	public static void main(String[] args) {
		ai.getAndSet(0, 3);
		System.out.println(ai.get(0));
                System.out.println(value[0]);
	}

}