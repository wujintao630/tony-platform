package com.tonytaotao.proxy;
import java.lang.reflect.Method;
public class $Proxy0 implements com.tonytaotao.proxy.People{
MyInvocationHandler h;
public $Proxy0(MyInvocationHandler h){
this.h=h;
}public void eat() throws Throwable{
Method md=com.tonytaotao.proxy.People.class.getMethod("eat",new Class[]{});
this.h.invoke(this,md,null);
}

}