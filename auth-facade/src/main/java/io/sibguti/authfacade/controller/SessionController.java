package io.sibguti.authfacade.controller;

import io.sibguti.authfacade.service.RedisSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @Autowired
    private RedisSessionService sessionService;

    @GetMapping("/check-session")
    public ResponseEntity<String> checkSession(@RequestParam("sessionId") String sessionId){
        var session = sessionService.getSession(sessionId);
        if (session != null)
            return ResponseEntity.ok(session);
        else
            return ResponseEntity.notFound().build();
    }
}
