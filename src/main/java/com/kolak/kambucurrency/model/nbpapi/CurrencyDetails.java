package com.kolak.kambucurrency.model.nbpapi;

import java.util.List;

public class CurrencyDetails {

    private String code;
    private List<Rate> rates;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
