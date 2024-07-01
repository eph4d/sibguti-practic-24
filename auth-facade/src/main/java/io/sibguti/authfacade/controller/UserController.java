package io.sibguti.authfacade.controller;


//package io.sibguti.authfacade.controller;


import io.sibguti.authfacade.entity.Session;
import io.sibguti.authfacade.service.RedisSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.Duration;

@RestController
public class UserController {

    @Autowired
    private RedisSessionService sessionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @GetMapping("/api/user-info")
    @ResponseBody
    public String getUser(@CookieValue("JSESSIONID") Cookie cookie, HttpSession session, HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        if (cookie == null) {
            return "session not found";
        }
        var securityContext = securityContextRepository.loadDeferredContext(request).get();


        if (securityContext == null) {
            return "No security context found in session";
        }
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return "No authentication found in security context";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return "Cookie name: " + cookie.getName() + ", SESSION ID: " + cookie.getValue() +
                "\nUsername: " + userDetails.getUsername() + ", Password: " + userDetails.getPassword();
    }

    @PostMapping("/api/login")
    public void login(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
            ) throws IOException
    {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        Session customSession = sessionService.createSession(principal, Duration.ofMinutes(30));
        sessionService.saveSession(customSession);

        Cookie cookie = new Cookie("JSESSIONID", customSession.getId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

