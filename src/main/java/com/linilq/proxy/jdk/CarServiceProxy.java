package com.linilq.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class CarServiceProxy implements InvocationHandler {

    private CarService object;

    public Object getProxyInstance(CarService object) {
        this.object = object;
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理开始，可以做前置工作");
        Object ret = method.invoke(object,args);
        System.out.println("代理结束，可以做收尾工作");
        return ret;
    }
}
