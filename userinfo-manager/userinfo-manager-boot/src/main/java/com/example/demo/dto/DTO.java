package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DTO {
    private FullName fullName;
    private BirthDateInfo birthDateInfo;
    private PhoneInfo phoneInfo;
    private EmailInfoDto emailInfoDto;

    public DTO() {
        this.fullName = new FullName();
        this.birthDateInfo = new BirthDateInfo();
        this.phoneInfo = new PhoneInfo();
        this.emailInfoDto = new EmailInfoDto();
    }
}

@Data
class FullName {
    private String firstName;
    private String surName;
    private String patronymic;
}

@Data
class BirthDateInfo {
    private String date;
}

@Data
class PhoneInfo {
    private List<Phone> phones;

    public PhoneInfo() {
        this.phones = new ArrayList<>();
    }
}

@Data
class Phone {
    private String number;
}

@Data
class EmailInfoDto {
    private List<Email> emails;

    public EmailInfoDto() {
        this.emails = new ArrayList<>();
    }
}

@Data
class Email {
    private String address;
    private Boolean isConfirmed;
}