package com.kolak.kambucurrency.service;


import com.kolak.kambucurrency.exception.AmountMustBePositiveException;
import com.kolak.kambucurrency.exception.CurrencyNotSupportedException;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;

import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";
    private final static int MEDIUM_RATE = 0;

    private final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;
    private final PersistedRequestRepository persistedRequestRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, PersistedRequestRepository persistedRequestRepository) {
        this.currencyRepository = currencyRepository;
        this.persistedRequestRepository = persistedRequestRepository;
        restTemplate = new RestTemplate();
    }

    public List<String> getAllAvailableCurrencies() {

        return currencyRepository.findAll().stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());
    }

    public double convert(Double amount, String base, String desired) {
        double baseRate = getPlnRate(base);
        double desiredBase = getPlnRate(desired);

        if (amount < 1) {
            throw new AmountMustBePositiveException();
        }

        saveToDB(amount, base, Collections.singletonList(desired));

        return formatDouble((baseRate / desiredBase) * amount);
    }


    public Map<String, Double> getCurrencyRating(String base, List<String> currenciesList) {
        Map<String, Double> rates = new HashMap<>();

        if (currenciesList.isEmpty()) {
            return getCurrencyRating(base, rates);
        }

        for (String currency : currenciesList) {
            rates.put(currency.toUpperCase(),
                    formatDouble(getPlnRate(base) / getPlnRate(currency)));
        }

        saveToDB(null, base, currenciesList);
        return rates;
    }


    public Map<String, Double> getCurrencyRating(String base, Map<String, Double> rates) {
        saveToDB(null, base, getAllAvailableCurrencies());
        for (String currency : getAllAvailableCurrencies()) {
            rates.put(currency, formatDouble(getPlnRate(base) / getPlnRate(currency)));
        }
        return rates;
    }


    public double getPlnRate(String base) {
        if (currencyRepository.findAll().stream().noneMatch(currency -> currency.getCode().equals(base.toUpperCase()))) {
            throw new CurrencyNotSupportedException(base);
        }

        if (base.toUpperCase().equals("PLN")) {
            return 1.0d;
        }

        CurrencyDetails currencyDetails = restTemplate.getForObject(CURRENCY_API_URL + base + JSON_FORMAT, CurrencyDetails.class);
        double baseCurrencyMidRate = 0d;

        if (currencyDetails != null)
            return currencyDetails.getRates().get(MEDIUM_RATE).getMid();

        return baseCurrencyMidRate;
    }


    public Double formatDouble(double toFormat) {
        DecimalFormat df = new DecimalFormat("####.##");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(df.format(toFormat));
    }

    public void saveToDB(Double amount, String base, List<String> desired) {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setAmount(amount);
        persistedRequest.setBase(base.toUpperCase());
        persistedRequest.setDesired(desired.stream().map(String::toUpperCase).collect(Collectors.toList()));
        persistedRequest.setTimeCreated(LocalDateTime.now());

        persistedRequestRepository.save(persistedRequest);
    }

    public List<PersistedRequest> elo() {
        return persistedRequestRepository.findAll();
    }
}
