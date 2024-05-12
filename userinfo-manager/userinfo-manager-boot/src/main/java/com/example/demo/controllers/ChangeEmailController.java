package com.example.demo.controllers;

import com.example.demo.models.EmailAndCodeModel;
import com.example.demo.models.EmailModel;
import com.example.demo.models.OtpParamsModel;
import com.example.demo.util.*;
import com.example.demo.util.Error;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userinfo/email/change/otp")
public class ChangeEmailController {
    @PostMapping("/send")
    public ResponseEntity<Object> changeOtpSend(@RequestBody @Valid EmailModel model,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new addOtpSendException(Error.OPERATION_BLOCKED.toString()); //400
        } else if (false) {
            BadRequestModel_3 response = new BadRequestModel_3();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); //403
        } else if (false) {
            throw new addOtpSendException(Error.EMAIL_ALREADY_IN_USE.toString());//400
        } else {
            OtpParamsModel response = new OtpParamsModel();
            return ResponseEntity.status(HttpStatus.OK).body(response);        //200
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<Object> changeOtpConfirm(@RequestBody @Valid EmailAndCodeModel model,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {                                       //400
            throw new addOtpConfirmException(Error.INVALID_DATA.toString());
        } else if (false) {                                                     //400
            throw new addOtpConfirmException(Error.EMAIL_ALREADY_IN_USE.toString());
        } else {                                                               //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Добавление email прошло успешно");
        }
    }

    @ExceptionHandler
    private ResponseEntity<BadRequestModel_2> handleException(addOtpConfirmException exception) {
        BadRequestModel_2 response = new BadRequestModel_2(exception.getMessage(), "9");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<BadRequestModel> handleException(addOtpSendException exception) {
        BadRequestModel response = new BadRequestModel(exception.getMessage(), "20:15");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
