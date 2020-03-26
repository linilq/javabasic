package com.linilq.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/26
 */
public class CglibInvocationHandleProxy implements InvocationHandler {

    private MazdaCarService carService;
    private CglibInvocationHandleProxy(MazdaCarService carService){
        this.carService = carService;
    }
    public static MazdaCarService creatProxy(){
        CglibInvocationHandleProxy proxy = new CglibInvocationHandleProxy(new MazdaCarServiceImpl());
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(proxy);
        enhancer.setInterfaces(new Class[]{MazdaCarService.class});
        MazdaCarService enhancedService = (MazdaCarService) enhancer.create();
        return enhancedService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理开始，可以做前置工作");
        Object ret = method.invoke(carService,args);
        System.out.println("代理结束，可以做收尾工作");
        return ret;
    }
}
