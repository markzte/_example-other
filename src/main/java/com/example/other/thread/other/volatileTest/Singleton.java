package com.example.other.thread.other.volatileTest;

/**高效单例
 * @author binzhao
 * @date 2017年3月10日
 */
public class Singleton{ 
     private volatile  static Singleton instance; 
     private Singleton(){} 

     public static Singleton getInstance(){ 
            if(instance == null){ 
                   synchronized(Singleton.class){ 	//1
                        if(instance == null) 		//2
                        instance = new Singleton(); 	//3
                   }           
             } 
            return instance; 
    } 
} 