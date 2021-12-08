package com.midtermsecrets.midtermsecrets.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "secrets")
public class Secret {

    @Id
    @GeneratedValue
    private Long id;

    @Length(min = 1, max = 1000)
    private String body;

    private LocalDateTime createdDT;

    private LocalDateTime expiryDT;

    @Transient
    private String stringExpiryDT;

    private String secretToUpdate;

    private String secretToRead;

    private boolean hasBeenRead = false;

    public Secret(Long id, String body, LocalDateTime createdDT, String stringExpiryDT, LocalDateTime expiryDT, String secretToUpdate, String secretToRead, boolean hasBeenRead) {
        this.id = id;
        this.body = body;
        this.createdDT = createdDT;
        this.stringExpiryDT = stringExpiryDT;
        this.expiryDT = expiryDT;
        this.secretToUpdate = secretToUpdate;
        this.secretToRead = secretToRead;
        this.hasBeenRead = hasBeenRead;
    }

    public Secret() {

    }

    public Secret(String body, LocalDateTime expiryDT, String secretToUpdate) {
        this.body = body;
        this.expiryDT = expiryDT;
        this.secretToUpdate = secretToUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedDT() {
        return createdDT;
    }

    public void setCreatedDT(LocalDateTime createdDT) {
        this.createdDT = createdDT;
    }

    public String getStringExpiryDT() {
        return stringExpiryDT;
    }

    public void setStringExpiryDT(String stringExpiryDT) {
        this.stringExpiryDT = stringExpiryDT;
    }

    public LocalDateTime getExpiryDT() {
        return expiryDT;
    }

    public void setExpiryDT(LocalDateTime expiryDT) {
        this.expiryDT = expiryDT;
    }

    public String getSecretToUpdate() {
        return secretToUpdate;
    }

    public void setSecretToUpdate(String secretToUpdate) {
        this.secretToUpdate = secretToUpdate;
    }

    public String getSecretToRead() {
        return secretToRead;
    }

    public void setSecretToRead(String secretToRead) {
        this.secretToRead = secretToRead;
    }

    public boolean hasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    @Override
    public String toString() {
        return "Secret{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", createdDT=" + createdDT +
                ", expiryDT=" + expiryDT +
                ", secretToUpdate='" + secretToUpdate + '\'' +
                ", secretToRead='" + secretToRead + '\'' +
                ", hasBeenRead=" + hasBeenRead +
                '}';
    }
}
