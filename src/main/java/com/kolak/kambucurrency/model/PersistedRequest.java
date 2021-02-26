package com.kolak.kambucurrency.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
public class PersistedRequest {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestUrl;
    private LocalDateTime timeCreated;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double amount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String baseCurrency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection
    private Map<String, Double> desiredCurrencies;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection
    private List<String> invokedExternalApiUrls;

    public PersistedRequest() {
    }

    public PersistedRequest(String requestUrl, LocalDateTime timeCreated,
                            Double amount, String baseCurrency,
                            Map<String, Double> desiredCurrencies, List<String> invokedExternalApiUrls) {
        this.requestUrl = requestUrl;
        this.timeCreated = timeCreated;
        this.amount = amount;
        this.baseCurrency = baseCurrency;
        this.desiredCurrencies = desiredCurrencies;
        this.invokedExternalApiUrls = invokedExternalApiUrls;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<String, Double> getDesiredCurrencies() {
        return desiredCurrencies;
    }

    public void setDesiredCurrencies(Map<String, Double> desiredCurrencies) {
        this.desiredCurrencies = desiredCurrencies;
    }

    public List<String> getInvokedExternalApiUrls() {
        return invokedExternalApiUrls;
    }

    public void setInvokedExternalApiUrls(List<String> invokedExternalApiUrls) {
        this.invokedExternalApiUrls = invokedExternalApiUrls;
    }
}
