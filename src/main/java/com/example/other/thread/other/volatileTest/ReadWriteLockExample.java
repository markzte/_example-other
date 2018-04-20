package com.example.other.thread.other.volatileTest;

/**读写锁：一写多读
 * @author binzhao
 * @date 2017年3月10日
 */
public class ReadWriteLockExample {
	
	private volatile String name;
	
	/**读取
	 * @return
	 */
	public String read(){
		return name;
	}
	
	/**写入
	 * @param name
	 */
	public synchronized void write(String name){
		this.name = name;
	}
	
	
}
