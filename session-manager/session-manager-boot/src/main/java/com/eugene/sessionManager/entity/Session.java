package com.eugene.sessionManager.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    private String sessionId;
    private Integer creationTime;
    private Integer lastAccessTime;
    private Integer maxInactiveInterval;
    private Integer expiryTime;
    private String principalName;


    @ElementCollection
    @CollectionTable(name = "session_attributes", joinColumns = @JoinColumn(name = "session_id"))
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value")
    private Map<String, String> attributes;
}
