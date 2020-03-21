package com.linilq.proxy;

import com.linilq.proxy.jdk.CarService;
import com.linilq.proxy.jdk.CarServiceProxy;
import com.linilq.proxy.jdk.HongQiCarService;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        CarServiceProxy carServiceProxy = new CarServiceProxy();
        CarService carService = (CarService) carServiceProxy.getProxyInstance(new HongQiCarService());
        carService.createCar("烈焰红");
    }
}
