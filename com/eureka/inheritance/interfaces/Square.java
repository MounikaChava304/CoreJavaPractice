package com.eureka.inheritance.interfaces;

import java.math.BigDecimal;

public class Square implements Shape{
    private BigDecimal side;

    private Square() {

    }

    public Square(BigDecimal side) {
        this.side = side;
    }

    public BigDecimal getSide() {
        return side;
    }

    /***
     * This class is forced to override and implement this method as it is implementing the interface Shape
     * This method calculates the area of a square
     * @return area of a shape
     */
    @Override
    public BigDecimal calculateArea() {
        return side.multiply(side);
    }
}
