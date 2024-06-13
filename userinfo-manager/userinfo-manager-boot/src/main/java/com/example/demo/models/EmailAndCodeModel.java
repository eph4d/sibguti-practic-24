package com.example.demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAndCodeModel {
    @NotEmpty
    @Email
    @Size(max = 80)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]{1,64}@[a-zA-Z0-9-.]{1,185}\\.[a-zA-Z0-9-.]{2,6}$")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^[0-9]+$")
    private String code;
}
