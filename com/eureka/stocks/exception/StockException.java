package com.eureka.stocks.exception;

public class StockException extends RuntimeException{

    private StockException() {
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
