package com.kolak.kambucurrency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
public class PersistedRequest {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Double amount;
//    private String base;
//
//    @ElementCollection
//    private Map<String, Double> desiredCurrencies;

    private String requestUrl;
    private LocalDateTime timeCreated;

    public PersistedRequest() {
    }

    public PersistedRequest(String requestUrl, LocalDateTime timeCreated) {
        this.requestUrl = requestUrl;
        this.timeCreated = timeCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
