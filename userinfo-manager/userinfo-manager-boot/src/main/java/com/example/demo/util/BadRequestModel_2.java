package com.example.demo.util;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BadRequestModel_2 {
    private String error;
    private Params_2 params;

    public BadRequestModel_2(String error, String attemptsLeft) {
        this.error = error;
        Params_2 params = new Params_2(attemptsLeft);
        this.params = params;
    }
}
@Data
class Params_2 {
    @Pattern(regexp = "[0-9]{1,1}")
    private String attemptsLeft;

    public Params_2(String attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }
}