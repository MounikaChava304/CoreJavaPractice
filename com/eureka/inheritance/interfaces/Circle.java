package com.eureka.inheritance.interfaces;

import java.math.BigDecimal;

public class Circle implements Shape{
    private BigDecimal radius;

    private Circle() {
    }

    public Circle(BigDecimal radius) {
        this.radius = radius;
    }

    public BigDecimal getRadius() {
        return radius;
    }
    /***
     * This class is forced to override and implement this method as it is implementing the interface Shape
     * This method calculates the area of a circle
     * @return area of a shape
     */
    @Override
    public BigDecimal calculateArea() {
        return radius.multiply(radius).multiply(new BigDecimal(Math.PI));
    }
}
