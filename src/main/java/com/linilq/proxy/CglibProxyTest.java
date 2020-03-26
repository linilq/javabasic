package com.linilq.proxy;

import com.linilq.proxy.cglib.*;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\tmp\\cglibproxy");
//        invocationHandlerTest();
        methodInterceptorTest();
//        invocationHandlerTest();
    }

    /**
     * 要对比jdk动态代理和cglib那个性能更好，需要按下面的方式去对比；
     * 如果使用invokeSuperClass来最终调用方法，cglib没有什么优势；
     * 只有传入代理对象，使用proxyMethod.invoke(obj,args)的方式，它的优势才好体验出来，原因是：
     * 1、proxyMethod.invoke(obj,args)时，会初始化一个FastClassInfo,其中封装了代理类、被代理类，代理方法index、被代理方法index;
     * 2、进一步使用 * 被代理类 * 调用invoke方法，其中逻辑会根据被代理方法的id，决定执行代理类中的某个方法；
     * 如此，就绕过了反射；而这一前提就是，在运行时期第一次使用Enhancer获取代理对象时，
     * 生成两个FastClass-->代理类的FastClass、被代理类的FastClass；其结构主要是：
     * 1、提供获取index的方法；
     * 2、提供一个public的invoke(int methodIndex,Object obj,Object[] args)方法供调用；
     * 另外，生成一个集成自接口的代理类，包含MethodInterceptor以接收接口的调用；
     */
    private static void methodInterceptorInterfaceTest() {
        MazdaCarService mazdaCarService = new MazdaCarServiceImpl();
        MazdaCarService enhancedService = CglibMethodInterceptorInterfacesProxy.createProxy(mazdaCarService.getClass().getInterfaces(), mazdaCarService);
        enhancedService.createMzdaCar("皇家尊贵金");
    }

    /**
     * 如果使用invokeSuperClass来最终调用方法，那么传入的class就必须是实现接口方法的类
     */
    private static void methodInterceptorTest() {
//        MazdaCarService enhancedService = CglibMethodInterceptorSupperClassProxy.createProxy(MazdaCarService.class);
        MazdaCarService enhancedService = CglibMethodInterceptorSupperClassProxy.createProxy(MazdaCarServiceImpl.class);
        enhancedService.createMzdaCar("魂动红");
    }

    /**
     * cglib也自定义有一个InvocationHandler，用以兼容jdk1.3以前的版本，
     * 用法和jdk动态代理一样，最终调用仍然使用反射
     */
    private static void invocationHandlerTest() {
        MazdaCarService enhancedService = CglibInvocationHandleProxy.creatProxy();
        enhancedService.createMzdaCar("魂动红");
    }
}
