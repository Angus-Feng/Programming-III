package com.midtermsecrets.midtermsecrets.repository;

import com.midtermsecrets.midtermsecrets.entity.Secret;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findBySecretToRead(String secretToRead);

    Optional<Secret> findBySecretToUpdate(String secretToUpdate);

    Optional<List<Secret>> findAllByExpiryDTIsAfter(LocalDateTime now);

    Optional<List<Secret>> findAllByExpiryDTIsAfterAndHasBeenRead(LocalDateTime localDateTime, boolean unread);


    void deleteByExpiryDTBefore(LocalDateTime localDateTime);

    @Transactional
    @Modifying
    @Query("UPDATE Secret s SET s.hasBeenRead = TRUE WHERE s.secretToRead = ?1")
    void hasBeenRead(String secretToRead);

}
