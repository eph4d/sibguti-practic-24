package com.example.demo.util;

public class AddOtpSendException extends RuntimeException {
    /**
     * Конструктор с сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public AddOtpSendException(String message) {
        super(message);
    }
}
