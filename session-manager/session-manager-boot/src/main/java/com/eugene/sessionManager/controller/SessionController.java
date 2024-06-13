package com.eugene.sessionManager.controller;

import com.eugene.sessionManager.entity.Session;
import com.eugene.sessionManager.entity.SessionAttributeBytesDtoV1;
import com.eugene.sessionManager.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Контроллер для управления сессиями.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * Создает новую сессию.
     *
     * @param sessionId       идентификатор создаваемой сессии
     * @param requestSession  данные сессии
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @PostMapping("/{sessionId}")
    public ResponseEntity<Void> createSession(@PathVariable String sessionId, @RequestBody Session requestSession) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            sessionService.createSession(requestSession);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Обновляет существующую сессию.
     *
     * @param sessionId       идентификатор обновляемой сессии
     * @param requestSession  данные сессии
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @PutMapping("/{sessionId}")
    public ResponseEntity<Void> updateSession(@PathVariable String sessionId, @RequestBody Session requestSession) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            sessionService.updateSession(requestSession);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Находит сессию по ее идентификатору.
     *
     * @param sessionId идентификатор сессии
     * @return объект ResponseEntity, содержащий данные сессии или указывающий на то, что сессия не найдена
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<Session> findSession(@PathVariable String sessionId) {
        Optional<Session> session = sessionService.getSession(sessionId);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Удаляет сессию по ее идентификатору.
     *
     * @param sessionId идентификатор сессии
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            sessionService.deleteSession(sessionId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Находит сессии по имени пользователя (principalName).
     *
     * @param principalName имя пользователя для поиска
     * @return объект ResponseEntity, содержащий список сессий или указывающий на то, что сессии не найдены
     */
    @GetMapping("/findByPrincipalName")
    public ResponseEntity<List<Session>> findByPrincipalName(@RequestParam String principalName) {
        List<Session> sessionList = sessionService.findByPrincipalName(principalName);
        if (sessionList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sessionList);
        }
    }

    /**
     * Создает новый атрибут для сессии.
     *
     * @param sessionId           идентификатор сессии
     * @param attributeName       имя создаваемого атрибута
     * @param attributeBytesDtoV1 данные атрибута
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @PostMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<Void> createSessionAttribute(@PathVariable String sessionId, @PathVariable String attributeName, @RequestBody SessionAttributeBytesDtoV1 attributeBytesDtoV1) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            session.get().getAttributes().put(attributeName, attributeBytesDtoV1.getBytes());
            sessionService.updateSession(session.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновляет атрибут сессии.
     *
     * @param sessionId           идентификатор сессии
     * @param attributeName       имя атрибута для обновления
     * @param attributeBytesDtoV1 данные атрибута
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @PutMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<Void> updateAttribute(@PathVariable String sessionId, @PathVariable String attributeName, @RequestBody SessionAttributeBytesDtoV1 attributeBytesDtoV1) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            Map<String, String> attributes = session.get().getAttributes();
            if (attributes.containsKey(attributeName)) {
                attributes.put(attributeName, attributeBytesDtoV1.getBytes());
                sessionService.updateSession(session.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Удаляет атрибут сессии.
     *
     * @param sessionId     идентификатор сессии
     * @param attributeName имя атрибута для удаления
     * @return объект ResponseEntity, указывающий на результат выполнения
     */
    @DeleteMapping("/{sessionId}/attributes/{attributeName}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable String sessionId, @PathVariable String attributeName) {
        Optional<Session> session = sessionService.getSession(sessionId);
        if (session.isPresent()) {
            Map<String, String> attributes = session.get().getAttributes();
            if (attributes.containsKey(attributeName)) {
                attributes.remove(attributeName);
                sessionService.updateSession(session.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
