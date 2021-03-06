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
public class CglibMethodInterceptorSupperClassProxy implements MethodInterceptor {


    public CglibMethodInterceptorSupperClassProxy() {
    }

    public static MazdaCarService createProxy(Class supperClass) {
        CglibMethodInterceptorSupperClassProxy callback = new CglibMethodInterceptorSupperClassProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(supperClass);
        enhancer.setCallback(callback);

        return (MazdaCarService) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("CglibProxy1代理开始，可以做前置工作");
        Object ret = proxy.invokeSuper(obj, args);
        System.out.println("CglibProxy1代理结束，可以做收尾工作");
        return ret;
    }
}
