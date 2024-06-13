package com.eugene.sessionManager.service;

import com.eugene.sessionManager.entity.Session;
import com.eugene.sessionManager.entity.SessionAttributeBytesDtoV1;
import com.eugene.sessionManager.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void createSession(Session session){
        sessionRepository.save(session);
    }

    public void updateSession(Session session){
        sessionRepository.save(session);
    }

    public Optional<Session> getSession(String sessionId){
        return sessionRepository.findById(sessionId);
    }

    public void deleteSession(String sessionId){
        sessionRepository.deleteById(sessionId);
    }

    public List<Session> findByPrincipalName(String principalName){
        return sessionRepository.findByPrincipalName(principalName);
    }
}
