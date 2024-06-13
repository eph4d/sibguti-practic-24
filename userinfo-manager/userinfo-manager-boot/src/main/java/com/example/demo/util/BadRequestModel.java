package com.example.demo.util;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class BadRequestModel {
    private String error;
    private Params params;

    /**
     * Конструктор с сообщением об ошибке и параметрами.
     *
     * @param error сообщение об ошибке
     * @param unlockTime время разблокировки
     */
    public BadRequestModel(String error, String unlockTime) {
        this.error = error;
        Params params = new Params(unlockTime);
        this.params = params;
    }
}
@Data
class Params {
    @Pattern(regexp = "[0-9]{1,1}")
    private String unlockTime;

    /**
     * Конструктор с временем разблокировки.
     *
     * @param unlockTime время разблокировки
     */
    public Params(String unlockTime) {
        this.unlockTime = unlockTime;
    }
}
