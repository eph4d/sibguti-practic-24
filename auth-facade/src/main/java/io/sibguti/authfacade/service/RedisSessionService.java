package io.sibguti.authfacade.service;

import io.sibguti.authfacade.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RedisSessionService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void saveSession(Session session) {
        String key = session.getId();
        redisTemplate.opsForValue().set(key, session.getPayload().getName());
        Duration ttl = Duration.between(Instant.now(), session.getExpiryTime());
        redisTemplate.expire(key, ttl);
    }

    public String getSession(String sessionId) {
        return redisTemplate.opsForValue().get(sessionId);
    }

    public void deleteSession(String sessionId) {
        redisTemplate.delete(sessionId);
    }

    public Session createSession(Principal principal, Duration duration) {
        String sessionId = UUID.randomUUID().toString();
        Instant expiryTime = Instant.now().plus(duration);
        return new Session(sessionId, principal, expiryTime);
    }
}
