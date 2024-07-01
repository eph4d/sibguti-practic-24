package io.sibguti.authstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth-facade.url}")
    private String authFacadeUrl;

    public boolean checkSession(String sessionId) {
        String url = authFacadeUrl + "/check-session?sessionId=" + sessionId;

        try
        {
            restTemplate.exchange(url, HttpMethod.GET, null, Void.class);
            return true;
        }
        catch (HttpClientErrorException e){
            return false;
        }
    }
}
