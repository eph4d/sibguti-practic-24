package com.example.demo.models;

import lombok.Data;

import java.util.UUID;

@Data
public class OtpParamsModel {
    private String uuid;
    private long expired;

    public OtpParamsModel() {
        this.uuid = UUID.randomUUID().toString();
        long number = System.currentTimeMillis();
        String stringNumber = String.valueOf(number);
        String substring = stringNumber.substring(0, 10);
        this.expired = Integer.parseInt(substring);
    }
}
