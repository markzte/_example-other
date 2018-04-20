/*
 * 程序功能：演示了RandomAccessFile类的操作，同时实现了一个文件复制操作。
 */
package com.example.other.bigFile.RandomAccessFile;

import java.io.*;

/**RandomAccessFile的唯一父类是Object，与其他流父类不同。是用来访问那些保存数据记录的文件的，这样你就可以用seek( )方法来访问记录，并进行读写了。
 * 这些记录的大小不必相同；但是其大小和位置必须是可知的。
 * 
 * 此类的实例支持对随机访问文件的读取和写入。随机访问文件的行为类似存储在文件系统中的一个大型 byte 数组。存在指向该隐含数组的光标或索引，称为文件指针；
 * 输入操作从文件指针开始读取字节，并随着对字节的读取而前移此文件指针。如果随机访问文件以读取/写入模式创建，则输出操作也可用；
 * 输出操作从文件指针开始写入字节，并随着对字节的写入而前移此文件指针。写入隐含数组的当前末尾之后的输出操作导致该数组扩展。该文件指针可以通过 getFilePointer 方法读取，并通过 seek 方法设置。 
 * 通常，如果此类中的所有读取例程在读取所需数量的字节之前已到达文件末尾，则抛出 EOFException（是一种 IOException）。
 * 如果由于某些原因无法读取任何字节，而不是在读取所需数量的字节之前已到达文件末尾，则抛出 IOException，而不是 EOFException。需要特别指出的是，如果流已被关闭，则可能抛出 IOException。 
 * @author binzhao
 * @date 2017年1月12日
 */
public class RandomAccessFileDemo {
	public static void main(String[] args) throws Exception {
		RandomAccessFile file = new RandomAccessFile("file", "rw");
		// 以下向file文件中写数据
		file.writeInt(20);// 占4个字节
		file.writeDouble(8.236598);// 占8个字节
		file.writeUTF("这是一个UTF字符串");// 这个长度写在当前文件指针的前两个字节处，可用readShort()读取
		file.writeBoolean(true);// 占1个字节
		file.writeShort(395);// 占2个字节
		file.writeLong(2325451l);// 占8个字节
		file.writeUTF("又是一个UTF字符串");
		file.writeFloat(35.5f);// 占4个字节
		file.writeChar('a');// 占2个字节

		file.seek(0);// 把文件指针位置设置到文件起始处

		// 以下从file文件中读数据，要注意文件指针的位置
		System.out.println("——————从file文件指定位置读数据——————");
		System.out.println(file.readInt());
		System.out.println(file.readDouble());
		System.out.println(file.readUTF());

		file.skipBytes(3);// 将文件指针跳过3个字节，本例中即跳过了一个boolean值和short值。
		System.out.println(file.readLong());

		file.skipBytes(file.readShort()); // 跳过文件中“又是一个UTF字符串”所占字节，注意readShort()方法会移动文件指针，所以不用加2。
		System.out.println(file.readFloat());

		// 以下演示文件复制操作
		System.out.println("——————文件复制（从file到fileCopy）——————");
		file.seek(0);
		RandomAccessFile fileCopy = new RandomAccessFile("fileCopy", "rw");
		int len = (int) file.length();// 取得文件长度（字节数）
		byte[] b = new byte[len];
		file.readFully(b);
		fileCopy.write(b);
		System.out.println("复制完成！");
	}
	
	
	/**
	 * RandomAccessFile 插入写示例：
	 * @param skip 跳过多少过字节进行插入数据
	 * @param str 要插入的字符串
	 * @param fileName 文件路径
	 */
	public static void insert(long skip, String str, String fileName){
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName,"rw");
			if(skip <  0 || skip > raf.length()){
				System.out.println("跳过字节数无效");
				return;
			}
			byte[] b = str.getBytes();
			raf.setLength(raf.length() + b.length);
			for(long i = raf.length() - 1; i > b.length + skip - 1; i--){
				raf.seek(i - b.length);
				byte temp = raf.readByte();
				raf.seek(i);
				raf.writeByte(temp);
			}
			raf.seek(skip);
			raf.write(b);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
