package com.kolak.kambucurrency.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public class PersistedRequestDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double amount;
    private String base;
    private Map<String, Double> desired;
    private LocalDateTime timeCreated;

    public PersistedRequestDto() {
    }

    public PersistedRequestDto(Double amount, String base, Map<String, Double> desired, LocalDateTime timeCreated) {
        this.amount = amount;
        this.base = base;
        this.desired = desired;
        this.timeCreated = timeCreated;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getDesired() {
        return desired;
    }

    public void setDesired(Map<String, Double> desired) {
        this.desired = desired;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
