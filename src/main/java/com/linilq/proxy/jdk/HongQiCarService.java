package com.linilq.proxy.jdk;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/19
 */
public class HongQiCarService implements CarService {

    public Car createCar(String color) {
        Car car = new Car();
        car.setName("红旗");
        car.setPrice(170000);
        car.setColor(color);
        System.out.println("新车生产出来了："+car.toString());
        return car;
    }
}
