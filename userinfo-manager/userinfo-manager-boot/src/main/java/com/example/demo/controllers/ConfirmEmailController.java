package com.example.demo.controllers;

import com.example.demo.models.EmailAndCodeModel;
import com.example.demo.models.EmailModel;
import com.example.demo.models.OtpParamsModel;
import com.example.demo.util.BadRequestModel;
import com.example.demo.util.BadRequestModel_2;
import com.example.demo.util.Error;
import com.example.demo.util.AddOtpConfirmException;
import com.example.demo.util.AddOtpSendException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для подтверждения OTP (одноразовый пароль) для верификации email.
 */
@RestController
@RequestMapping("/api/v1/userinfo/email/confirm/otp")
public class ConfirmEmailController {

    /**
     * Отправляет OTP на указанный email адрес.
     *
     * @param model модель email, содержащая адрес email
     * @param bindingResult результат привязки, который может использоваться для проверки ошибок
     * @return объект ответа с кодом состояния и телом ответа
     * @throws AddOtpSendException в случае наличия ошибок во входных данных или если email уже используется
     */
    @PostMapping("/send")
    public ResponseEntity<Object> confirmOtpSend(@RequestBody @Valid EmailModel model,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {                                        //400
            throw new AddOtpSendException(Error.OPERATION_BLOCKED.toString());
        } else if (false) {
            throw new AddOtpSendException(Error.EMAIL_ALREADY_IN_USE.toString());//400
        } else {
            OtpParamsModel response = new OtpParamsModel();
            return ResponseEntity.status(HttpStatus.OK).body(response); //200
        }
    }

    /**
     * Подтверждает OTP и добавляет email к аккаунту пользователя.
     *
     * @param model модель email и кода, содержащая адрес email и OTP
     * @param bindingResult результат привязки, который может использоваться для проверки ошибок
     * @return объект ответа с кодом состояния и телом ответа
     * @throws AddOtpConfirmException в случае наличия ошибок во входных данных или если email уже используется
     */
    @PostMapping("/confirm")
    public ResponseEntity<Object> confirmOtpConfirm(@RequestBody @Valid EmailAndCodeModel model,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {                                //400
            throw new AddOtpConfirmException(Error.INVALID_DATA.toString());
        } else if (false) {                                             //400
            throw new AddOtpConfirmException(Error.EMAIL_ALREADY_IN_USE.toString());
        } else {                                                        //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Добавление email прошло успешно");
        }
    }

    /**
     * Обработчик исключений для {@link AddOtpSendException}.
     *
     * @param exception исключение
     * @return объект ответа с кодом состояния и телом ответа
     */
    @ExceptionHandler
    private ResponseEntity<BadRequestModel> handleException(AddOtpSendException exception) {
        BadRequestModel response = new BadRequestModel(exception.getMessage(), "20:15");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Обработчик исключений для {@link AddOtpConfirmException}.
     *
     * @param exception исключение
     * @return объект ответа с кодом состояния и телом ответа
     */
    @ExceptionHandler
    private ResponseEntity<BadRequestModel_2> handleException(AddOtpConfirmException exception) {
        BadRequestModel_2 response = new BadRequestModel_2(exception.getMessage(), "9");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}