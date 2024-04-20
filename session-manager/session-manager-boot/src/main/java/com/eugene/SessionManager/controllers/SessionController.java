package com.eugene.SessionManager.controllers;

import com.eugene.SessionManager.dto.SessionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final Map<String, SessionDto> sessions = new ConcurrentHashMap<>();

    @PostMapping("/{sessionId}")
    public ResponseEntity<?> createSession(@PathVariable String sessionId, @RequestBody SessionDto session) {
        if (!session.getSessionId().equals(sessionId) || sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        sessions.put(sessionId, session);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(@PathVariable String sessionId, @RequestBody SessionDto session) {
        if (!session.getSessionId().equals(sessionId) || !sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sessions.put(sessionId, session);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SessionDto session = sessions.get(sessionId);
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sessions.remove(sessionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/findByPrincipalName")
    public ResponseEntity<?> findSessionsByPrincipalName(@RequestParam String principalName)
    {
        if (principalName == null || principalName.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var foundSessions = sessions.values().stream().filter(x->x.getPrincipalName().equals(principalName)).toList();

        return foundSessions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(foundSessions);
    }

    @PostMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<?> createSessionAttribute(@PathVariable String sessionId,
                                                    @PathVariable String attributeName,
                                                    @RequestBody String attributeValue)
    {
        if (!sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sessions.get(sessionId).getAttributes().put(attributeName, attributeValue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<?> updateSessionAttribute(@PathVariable String sessionId,
                                                    @PathVariable String attributeName,
                                                    @RequestBody String attributeValue) {
        if (!sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SessionDto session = sessions.get(sessionId);
        session.getAttributes().put(attributeName, attributeValue);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<?> deleteSessionAttribute(@PathVariable String sessionId,
                                                    @PathVariable String attributeName) {
        if (!sessions.containsKey(sessionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SessionDto session = sessions.get(sessionId);
        if (session.getAttributes().remove(attributeName) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
