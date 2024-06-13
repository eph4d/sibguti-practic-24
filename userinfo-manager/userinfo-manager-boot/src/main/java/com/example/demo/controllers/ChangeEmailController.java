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

/**
 * Контроллер для смены email и верификации через OTP
 */
@RestController
@RequestMapping("/api/v1/userinfo/email/change/otp")
public class ChangeEmailController {

    /**
     * Отправляет OTP код на email
     *
     * @param model       Модель email
     * @param bindingResult Результат привязки
     * @return ResponseEntity с OTP параметрами
     * @throws AddOtpSendException если операция заблокирована или email уже используется
     */
    @PostMapping("/send")
    public ResponseEntity<Object> changeOtpSend(@RequestBody @Valid EmailModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AddOtpSendException(Error.OPERATION_BLOCKED.toString());
        } else if (false) {
            BadRequestModel_3 response = new BadRequestModel_3();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else if (false) {
            throw new AddOtpSendException(Error.EMAIL_ALREADY_IN_USE.toString());
        } else {
            OtpParamsModel response = new OtpParamsModel();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * Подтверждает OTP код и меняет email в аккаунте пользователя
     *
     * @param model       Модель email и кода
     * @param bindingResult Результат привязки
     * @return ResponseEntity с сообщением об успехе
     * @throws AddOtpConfirmException если данные неверны или email уже используется
     */
    @PostMapping("/confirm")
    public ResponseEntity<Object> changeOtpConfirm(@RequestBody @Valid EmailAndCodeModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AddOtpConfirmException(Error.INVALID_DATA.toString());
        } else if (false) {
            throw new AddOtpConfirmException(Error.EMAIL_ALREADY_IN_USE.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Добавление email прошло успешно");
        }
    }

    /**
     * Обработчик исключений для {@link AddOtpConfirmException}.
     *
     * @param exception AddOtpConfirmException
     * @return ResponseEntity с BadRequestModel_2
     */
    @ExceptionHandler
    private ResponseEntity<BadRequestModel_2> handleException(AddOtpConfirmException exception) {
        BadRequestModel_2 response = new BadRequestModel_2(exception.getMessage(), "9");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Обработчик исключений для {@link AddOtpSendException}.
     *
     * @param exception AddOtpSendException
     * @return ResponseEntity с BadRequestModel
     */
    @ExceptionHandler
    private ResponseEntity<BadRequestModel> handleException(AddOtpSendException exception) {
        BadRequestModel response = new BadRequestModel(exception.getMessage(), "20:15");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}