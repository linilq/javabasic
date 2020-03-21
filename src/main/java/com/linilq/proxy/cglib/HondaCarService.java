package com.linilq.proxy.cglib;

import com.linilq.proxy.jdk.Car;
import com.linilq.proxy.jdk.CarService;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class HondaCarService implements CarService {
    @Override
    public Car createCar(String color) {
        Car car = new Car();
        car.setName("雅阁");
        car.setPrice(230000);
        car.setColor(color);
        System.out.println("新车生产出来了："+car.toString());
        return car;
    }
}
