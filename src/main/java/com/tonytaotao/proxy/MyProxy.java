package com.tonytaotao.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyProxy {

	static String rt = "\r\n";

	public static Object createProxyInstance(ClassLoader loader, Class clazz, MyInvocationHandler handler) {
		try {
			Method[] methods = clazz.getMethods();
			// 1、用流的方式创建一个java文件
			String proxyClass = "package com.tonytaotao.proxy;" + rt
					+ "import java.lang.reflect.Method;" + rt
					+ "public class $Proxy0 implements " + clazz.getName()+ "{" + rt
					+ "MyInvocationHandler h;" + rt
					+ "public $Proxy0(MyInvocationHandler h){" + rt
					+ "this.h=h;" + rt 
					+ "}" 
					+ getMethodString(methods, clazz)+ rt 
					+ "}";
			;

			// 2、把类生成文件
			String fileName = "E:/WorkSpace/IDEAworkspace/tony-platform/learn-practice/src/main/java/com/tonytaotao/proxy/$Proxy0.java";
			File f = new File(fileName);
			FileWriter fw = new FileWriter(f);
			fw.write(proxyClass);
			fw.flush();
			fw.close();
			
			//3、编译java文件
			JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileMgr=compiler.getStandardFileManager(null, null, null);
			Iterable units=fileMgr.getJavaFileObjects(fileName);
			CompilationTask task=compiler.getTask(null, fileMgr, null, null, null, units);
			task.call();
			fileMgr.close();
			
			//4、把class文件加载到内存
			MyClassLoader myClassLoader=new MyClassLoader("E:/WorkSpace/IDEAworkspace/tony-platform/learn-practice/src/main/java/com/tonytaotao/proxy/");
			Class proxy0Class=myClassLoader.findClass("$Proxy0");
			Constructor constructor=proxy0Class.getConstructor(MyInvocationHandler.class);
			Object o=constructor.newInstance(handler);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getMethodString(Method[] methods, Class clazz) {
		String proxyMethod = "";
		for (Method method : methods) {
			proxyMethod += "public void " + method.getName()+ "() throws Throwable{" + rt 
					+ "Method md="+ clazz.getName() + ".class.getMethod(\""+ method.getName() + "\",new Class[]{});" + rt
					+ "this.h.invoke(this,md,null);" + rt +
					"}" + rt;
		}
		return proxyMethod;
	}
}
