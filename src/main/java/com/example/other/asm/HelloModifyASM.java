package com.example.other.asm;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HelloModifyASM {
	public static void main(String[] args) throws Exception {
		URL url = HelloModifyASM.class.getResource("Foo.class");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new ClassAdapter(cw) {
			public void visit(int version, int access, String name,
					String signature, String superName, String[] interfaces) {
				super.visit(version, access, name, signature, superName,
						interfaces);
				// 添加字段：public static List _my_instances;
				super.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
						"_my_instances", "Ljava/util/List;", null, null);
				// 添加静态的初始化块
				MethodVisitor mv = super.visitMethod(Opcodes.ACC_STATIC, "",
						"()V", null, null);
				mv.visitCode();
				mv.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
				mv.visitInsn(Opcodes.DUP);
				mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
						"java/util/ArrayList", "", "()V");
				mv.visitFieldInsn(Opcodes.PUTSTATIC, "my/Foo", "_my_instances",
						"Ljava/util/List;");
				mv.visitInsn(Opcodes.RETURN);
				mv.visitMaxs(0, 0);
				mv.visitEnd();
			}

			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				MethodVisitor mv = super.visitMethod(access, name, desc,
						signature, exceptions);
				// 修改无参的构造函数：
				if (!"".equals(name) || !"()V".equals(desc))
					return mv;
				return new MethodAdapter(mv) {
					public void visitInsn(int opcode) {
						if (opcode == Opcodes.RETURN) {
							visitFieldInsn(Opcodes.GETSTATIC, "my/Foo",
									"_my_instances", "Ljava/util/List;");
							visitVarInsn(Opcodes.ALOAD, 0);
							visitMethodInsn(Opcodes.INVOKEINTERFACE,
									"java/util/List", "add",
									"(Ljava/lang/Object;)Z");
						}
						super.visitInsn(opcode);
					}
				};
			}
		};
		ClassReader cr = new ClassReader(url.openStream());
		cr.accept(cv, ClassReader.SKIP_DEBUG);
		final byte[] bs = cw.toByteArray();
		OutputStream out = new FileOutputStream("d:/my/Foo.class");
		out.write(bs);
		out.close();
		Class clazz = new ClassLoader(null) {
			public Class findClass(String name) throws ClassNotFoundException {
				if (!"my.Foo".equals(name))
					return ClassLoader.getSystemClassLoader().loadClass(name);
				return defineClass(name, bs, 0, bs.length);
			}
		}.loadClass("my.Foo");
		clazz.newInstance();
		clazz.newInstance();
		Field field = clazz.getField("_my_instances");
		List instances = (List) field.get(null);
		System.out.println(instances.size());
		for (Object obj : instances) {
			System.out.println(obj);
		}
	}
}