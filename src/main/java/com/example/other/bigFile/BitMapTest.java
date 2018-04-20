package com.example.other.bigFile;

/**BitMap从字面的意思，很多人认为是位图，其实准确的来说，翻译成基于位的映射，怎么理解呢？
 * 一台主机，2G内存，40亿个不重复的没排过序的unsigned int的整数的文件，然后再给一个整数，如何快速判断这个整数是否在那40亿个数当中？
 * 解决方案：
 * 将整数映射到bit上，例如整数10，10/8=1,10%8=2,那么就将a[1]的b[2]置为1。这样时间复杂度即是O(1),内存也得到了压缩。
 * 
 * 
 * 
 * bitmap算法概述：所谓bitmap就是用一个bit位来标记某个元素对应的value，而key即是这个元素。由于采用bit为单位来存储数据，因此在可以大大的节省存储空间
 * 算法思想：
 * 32位机器上，一个整形，比如int a;在内存中占32bit，可以用对应的32个bit位来表示十进制的0-31个数，bitmap算法利用这种思想处理大量数据的排序与查询
 * 	优点：
 * 		效率高，不许进行比较和移位
 * 		占用内存少，比如N=10000000;只需占用内存为N/8 = 1250000Bytes = 1.2M，如果采用int数组存储，则需要38M多
 * 	缺点：
 * 		无法对存在重复的数据进行排序和查找
 * @date 2017年9月27日
 */
public class BitMapTest {
    //保存数据的
    private byte[] bits;

    //能够存储多少数据
    private int capacity;


    public BitMapTest(int capacity){
        this.capacity = capacity;

        //1bit能存储8个数据，那么capacity数据需要多少个bit呢，capacity/8+1,右移3位相当于除以8
        bits = new byte[(capacity >>3 )+1];
    }

    public void add(int num){
        // num/8得到byte[]的index
        int arrayIndex = num >> 3; 

        // num%8得到在byte[index]的位置
        int position = num & 0x07; 

        //将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了。
        bits[arrayIndex] |= 1 << position; 
    }

    public boolean contain(int num){
        // num/8得到byte[]的index
        int arrayIndex = num >> 3; 

        // num%8得到在byte[index]的位置
        int position = num & 0x07; 

        //将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
        return (bits[arrayIndex] & (1 << position)) !=0; 
    }

    public void clear(int num){
        // num/8得到byte[]的index
        int arrayIndex = num >> 3; 

        // num%8得到在byte[index]的位置
        int position = num & 0x07; 

        //将1左移position后，那个位置自然就是1，然后对取反，再与当前值做&，即可清除当前的位置了.
        bits[arrayIndex] &= ~(1 << position); 

    }

    public static void main(String[] args) {
        BitMapTest bitmap = new BitMapTest(100);
        bitmap.add(7);
        System.out.println("插入7成功");

        boolean isexsit = bitmap.contain(7);
        System.out.println("7是否存在:"+isexsit);

        bitmap.clear(7);
        isexsit = bitmap.contain(7);
        System.out.println("7是否存在:"+isexsit);
    }
}

