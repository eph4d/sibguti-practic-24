package io.sibguti.authfacade.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session implements Serializable
{
    @Id
    private String id;
    private Principal payload;
    private Instant expiryTime;
}

