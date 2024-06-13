package com.example.demo.util;

public class AddOtpConfirmException extends RuntimeException {
    /**
     * Конструктор с сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public AddOtpConfirmException(String message) {
        super(message);
    }
}