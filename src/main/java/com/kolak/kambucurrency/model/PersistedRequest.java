package com.kolak.kambucurrency.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PersistedRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double amount;
    private String base;

    @ElementCollection
    private List<String> desired;

    private LocalDateTime timeCreated;

    public PersistedRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getDesired() {
        return desired;
    }

    public void setDesired(List<String> desired) {
        this.desired = desired;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "PersistedRequest{" +
                "id=" + id +
                ", amount=" + amount +
                ", base='" + base + '\'' +
                ", desired=" + desired +
                ", timeCreated=" + timeCreated +
                '}';
    }
}
