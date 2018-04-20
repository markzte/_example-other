package com.example.other;

/**质数
 * 
 * 质数又称素数：一个大于1的自然数，除了1和它自身外，不能被其他自然数整除的数；
 * 合数：比1大但不是素数的数，1和0即非素数也非合数。素数的属性称为素性，素数在数论中处于基本的重要地位。
 * @author binzhao
 * @date 2017年1月12日
 */
public class PrimeNumbers {

	public static void main(String[] args) {
		int j;
		for (int i = 2; i <= 100; i++) { 	// 1不是素数，所以直接从2开始循环
			j = 2;
			while (i % j != 0) {
				j++; 						// 测试2至i的数字是否能被i整除，如不能就自加
			}
			if (j == i)  {					// 当有被整除的数字时，判断它是不是自身
				System.out.println(i); 		// 如果是就打印出数字
			}
		}
//		System.out.println(3%3);
	}

}