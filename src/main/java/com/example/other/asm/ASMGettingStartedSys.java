package com.example.other.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**ClassWriter重新构建编译后的类
 * 示例一： 这里打算用ASM动态生成如下的类
 * 
 * package com.agent.my3;
 * public class Tester
 * {
 * 	public void run()
 * 	{
 * 		System.out.println("This is my first ASM test");
 * 	}
 * }
 * @author binzhao
 * @date 2018年4月20日
 */
public class ASMGettingStartedSys {
	/**
	 * 动态创建一个类，有一个无参数的构造函数
	 */
	static ClassWriter createClassWriter(String className) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		// 声明一个类，使用JDK1.8版本，public的类，父类是java.lang.Object，没有实现任何接口
		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, className, null,
				"java/lang/Object", null);

		//添加无参构造方法
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC,"<init>", "()V", null, null);
		// 这里请看截图
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		// 执行父类的init初始化
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object","<init>", "()V");
		// 从当前方法返回void
		constructor.visitInsn(Opcodes.RETURN);
		constructor.visitMaxs(1, 1);
		constructor.visitEnd();
		return cw;
	}

	/**
	 * 创建一个run方法，里面只有一个输出 public void run() { System.out.println(message); }
	 * 
	 * @return
	 * @throws Exception
	 */
	static byte[] createVoidMethod(String className, String message)
			throws Exception {
		// 注意，这里需要把classname里面的.改成/，如com.asm.Test改成com/asm/Test
		ClassWriter cw = createClassWriter(className.replace('.', '/'));

		// 创建run方法
		// ()V表示函数，无参数，无返回值
		MethodVisitor runMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "run","()V", null, null);
		// 先获取一个java.io.PrintStream对象
		runMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out","Ljava/io/PrintStream;");
		// 将int, float或String型常量值从常量池中推送至栈顶 (此处将message字符串从常量池中推送至栈顶[输出的内容])
		runMethod.visitLdcInsn(message);
		// 执行println方法（执行的是参数为字符串，无返回值的println函数）
		runMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println", "(Ljava/lang/String;)V");
		runMethod.visitInsn(Opcodes.RETURN);
		runMethod.visitMaxs(1, 1);
		runMethod.visitEnd();

		return cw.toByteArray();
	}

	public static void main(String[] args) throws Exception {
		String className = "com.agent.my3.Tester";
		byte[] classData = createVoidMethod(className,"This is my first ASM test");
		Class<?> clazz = new MyClassLoader().defineClassForName(className,classData);
		clazz.getMethods()[0].invoke(clazz.newInstance());
	}
}