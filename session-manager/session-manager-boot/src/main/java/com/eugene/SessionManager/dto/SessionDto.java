package com.eugene.SessionManager.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class SessionDto {
    private String sessionId;
    private int creationTime;
    private int lastAccessTime;
    private int maxInactiveInterval;
    private int expiryTime;
    private String principalName;
    private Map<String, String> attributes;
}

