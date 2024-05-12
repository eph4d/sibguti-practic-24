package com.example.demo.util;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class BadRequestModel {
    private String error;
    private Params params;

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

    public Params(String unlockTime) {
        this.unlockTime = unlockTime;
    }
}
