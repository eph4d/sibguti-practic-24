package com.example.demo.controllers;

import com.example.demo.models.EditFirstNameAndSurnameAndBirthdateModel;
import com.example.demo.dto.DTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userinfo")
public class UserInfoController {
    @GetMapping("")
    public ResponseEntity<Object> getUserInfo(DTO models) {
        if (false) { // в будущем проверка на авторизацию
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Запрос не авторизован"); //401
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(models); //200
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> editFirstNameAndSurnameAndBirthdate(@RequestBody @Valid
                                                                          EditFirstNameAndSurnameAndBirthdateModel model,
                                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка валидации тела запроса"); //400
        } else if (false) { // в будущем проверка на авторизацию
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Запрос не авторизован"); //401
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Редактирование данных прошло успешно"); //204
        }
    }
}