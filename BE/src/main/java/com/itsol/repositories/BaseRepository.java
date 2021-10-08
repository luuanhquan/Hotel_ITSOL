package com.itsol.repositories;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Data
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseRepository {
    @PersistenceContext
    EntityManager entityManager;
}
