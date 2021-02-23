package com.kolak.kambucurrency.service;


import com.kolak.kambucurrency.model.nbpapi.NbpApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";
    private final static int MEDIUM_RATE = 0;

    private final RestTemplate restTemplate;

    // http://api.nbp.pl/api/exchangerates/rates/a/usd

    public CurrencyService() {
        restTemplate = new RestTemplate();
    }

    public Map<String, Double> getCurrencyRating(List<String> currencyList) {
        Map<String, Double> rates = new HashMap<>();
        NbpApiResponse nbpApiResponse;
        for (String currency : currencyList) {
            nbpApiResponse = restTemplate.getForObject(CURRENCY_API_URL + currency + JSON_FORMAT, NbpApiResponse.class);

            if (nbpApiResponse != null)
                rates.put(currency.toUpperCase(), nbpApiResponse.getRates().get(MEDIUM_RATE).getMid());
        }

        return rates;
    }
}
