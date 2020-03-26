package com.linilq.proxy.cglib;

import com.linilq.proxy.jdk.Car;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/26
 */
public class MazdaCarServiceImpl implements MazdaCarService {
    @Override
    public Car createMzdaCar(String color) {
        Car car = new Car();
        car.setName("马自达");
        car.setPrice(230000);
        car.setColor(color);
        System.out.println("新车生产出来了：" + car.toString());
        return car;
    }
}
