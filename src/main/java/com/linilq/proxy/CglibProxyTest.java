package com.linilq.proxy;

import com.linilq.proxy.cglib.CglibProxy;
import com.linilq.proxy.cglib.HondaCarService;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\tmp\\cglibproxy");
        CglibProxy cglibProxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(cglibProxy);
        enhancer.setSuperclass(HondaCarService.class);
        HondaCarService enhancedService = (HondaCarService) enhancer.create();
        enhancedService.createCar("魂动红");
    }
}
