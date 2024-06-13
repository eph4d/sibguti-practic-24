package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditFirstNameAndSurnameAndBirthdateModel {
    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁA-Za-z0-9ÁÄӘЄЎČĆÇҒĐĎÉĚĞıÍЇҚЉŇҢЊÓÖŐӨŘŠŞßŤҰÚŮÜŰÝҺŽЂЋЏ.?\\s\\-`]+$")
    private String familyName;
    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁA-Za-z0-9ÁÄӘЄЎČĆÇҒĐĎÉĚĞıÍЇҚЉŇҢЊÓÖŐӨŘŠŞßŤҰÚŮÜŰÝҺŽЂЋЏ.?\\s\\-`]+$")
    private String givenName;
    @NotBlank
    @Pattern(regexp = "^((((19|20)[0-9]{2}-(0?[13578]|1[02])-(0?[1-9]|[12][0-9]|3[01]))|((19|20)[0-9]{2}-(0?[469]|11)-(0?[1-9]|[12][0-9]|30))|((19|20)[0-9]{2}-0?[2]-(0?[1-9]|1[0-9]|2[0-8]))|(((19|20)(04|08|[2468][048]|[13579][26]))|2000)-(0?2)-29))$")
    private String birthDate;
}
