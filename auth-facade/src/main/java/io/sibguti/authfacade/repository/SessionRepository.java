package io.sibguti.authfacade.repository;

import io.sibguti.authfacade.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
}
