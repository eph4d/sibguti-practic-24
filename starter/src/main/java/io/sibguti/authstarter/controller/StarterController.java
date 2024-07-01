package io.sibguti.authstarter.controller;


import io.sibguti.authstarter.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarterController {

    @Autowired
    private AuthService authService;

    @GetMapping("/starter/check-session")
    public boolean checkUserSession(@CookieValue(name = "JSESSIONID") Cookie cookie) {
        if (cookie != null){
            return authService.checkSession(cookie.getValue());
        }
        return false;
    }
}

