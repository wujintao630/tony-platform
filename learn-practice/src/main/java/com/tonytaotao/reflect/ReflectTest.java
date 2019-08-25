package com.tonytaotao.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectTest {

    public static void main(String[] args) throws Exception{

        Class clazz = Class.forName("com.tonytaotao.reflect.Demo");
        Demo demo = (Demo) clazz.newInstance();

        System.out.println("Integer A before : " + demo.getInteger());
        Field f1 = demo.getClass().getDeclaredField("A");
        f1.setAccessible(true);
        f1.set(demo, 2);
        f1.setAccessible(false);
        System.out.println("Integer A after : " + demo.getInteger());

        System.out.println("Integer B before : " + demo.getInt());
        Field f2 = demo.getClass().getDeclaredField("B");
        f2.setAccessible(true);
        f2.set(demo, 2);
        f2.setAccessible(false);
        System.out.println("Integer B after : " + demo.getInt());


        Class clazz1 = Class.forName("com.tonytaotao.reflect.DemoStatic");
        DemoStatic demo1 = (DemoStatic) clazz1.newInstance();

        System.out.println("Static Integer A before : " + demo1.getInteger());
        Field f3 = demo1.getClass().getDeclaredField("A");
        f3.setAccessible(true);
        Field f33 = f3.getClass().getDeclaredField("modifiers");
        f33.setAccessible(true);
        f33.setInt(f3, f3.getModifiers() & ~Modifier.FINAL);
        f3.set(null, 2);
        System.out.println("Static Integer A after : " + demo1.getInteger());

        System.out.println("Static Integer B before : " + demo1.getInt());
        Field f4 = demo1.getClass().getDeclaredField("B");
        f4.setAccessible(true);
        Field f44 = f4.getClass().getDeclaredField("modifiers");
        f44.setAccessible(true);
        f44.setInt(f4, f4.getModifiers() & ~Modifier.FINAL);
        f4.set(null, 2);
        System.out.println("Static Integer B after : " + demo1.getInt());
    }

}

class Demo {
    private  final Integer A = 1;

    private  final int B = 1;

    public Integer getInteger() {
        return A;
    }

    public int getInt() {
        return B;
    }
}

class DemoStatic {
    private static final Integer A = 1;

    private static final int B = 1;

    public Integer getInteger() {
        return A;
    }

    public int getInt() {
        return B;
    }
}

