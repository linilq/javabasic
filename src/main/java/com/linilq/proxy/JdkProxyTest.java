package com.linilq.proxy;

import com.linilq.proxy.jdk.CarService;
import com.linilq.proxy.jdk.CarServiceProxy;
import com.linilq.proxy.jdk.HongQiCarService;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class JdkProxyTest {

    public static void main(String[] args) {
//        打开注释后可输出动态代理类class文件，便于理解其工作机制
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        CarServiceProxy carServiceProxy = new CarServiceProxy();
        CarService carService = (CarService) carServiceProxy.getProxyInstance(new HongQiCarService());
        carService.createCar("烈焰红");
    }
}
