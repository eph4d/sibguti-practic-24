package com.eugene.sessionManager.repository;


import com.eugene.sessionManager.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String>
{
    List<Session> findByPrincipalName(String principalName);
}
