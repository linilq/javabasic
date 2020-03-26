package com.linilq.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class CglibMethodInterceptorInterfacesProxy implements MethodInterceptor {
    MazdaCarService mazdaCarService;

    public CglibMethodInterceptorInterfacesProxy(MazdaCarService mazdaCarService) {
        this.mazdaCarService = mazdaCarService;
    }

    public static MazdaCarService createProxy(Class[] interfaces, MazdaCarService mazdaCarService) {
        CglibMethodInterceptorInterfacesProxy callback = new CglibMethodInterceptorInterfacesProxy(mazdaCarService);
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(interfaces);
        enhancer.setCallback(callback);
        return (MazdaCarService) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("代理开始，可以做前置工作");
        Object ret = proxy.invoke(mazdaCarService, args);
        System.out.println("代理结束，可以做收尾工作");
        return ret;
    }
}
