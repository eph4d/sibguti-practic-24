package com.example.demo.util;

import lombok.Data;

@Data
public class BadRequestModel_3 {
    private String error = Error.PERMISSION_DENIED.toString();
}
