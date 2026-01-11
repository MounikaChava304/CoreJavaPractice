package com.eureka.inheritance.interfaces;

import java.math.BigDecimal;

public class Rectangle implements Shape {
    private BigDecimal length;
    private BigDecimal width;

    @Override
    public String toString() {
        return "Rectangle{" +
                "length=" + length +
                ", width=" + width +
                '}';
    }

    private Rectangle() {
    }

    public Rectangle(BigDecimal length, BigDecimal width) {
        this.length = length;
        this.width = width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    /***
     * This class is forced to override and implement this method as it is implementing the interface Shape
     * This method calculates the area of a rectangle
     * @return area of a shape
     */
    @Override
    public BigDecimal calculateArea() {
        return length.multiply(width);
    }
}
