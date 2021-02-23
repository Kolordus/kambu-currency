package com.kolak.kambucurrency.service;


import com.kolak.kambucurrency.exception.CurrencyNotSupportedException;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;

import com.kolak.kambucurrency.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";
    private final static int MEDIUM_RATE = 0;

    private final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
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

        return formatDouble((baseRate / desiredBase) * amount);
    }


    public Map<String, Double> getCurrencyRating(String base, List<String> currenciesList) {
        Map<String, Double> rates = new HashMap<>();

        if (base == null) {
            return getCurrencyRating(currenciesList, rates);
        }

        double baseRate = getPlnRate(base);

        if (currenciesList.isEmpty()) {
            return getCurrencyRating(baseRate, rates);
        }

        for (String currency : currenciesList) {
            rates.put(currency.toUpperCase(),
                    formatDouble(baseRate / getPlnRate(currency)));
        }

        return rates;
    }

    public Map<String, Double> getCurrencyRating(List<String> currenciesList, Map<String, Double> rates) {
        if (currenciesList.isEmpty()) {
            return getCurrencyRating(rates);
        }

        for (String currency : currenciesList) {
            rates.put(currency.toUpperCase(), getPlnRate(currency));
        }

        return rates;
    }

    public Map<String, Double> getCurrencyRating(double baseRate, Map<String, Double> rates) {
        for (Currency currency : currencyRepository.findAll()) {
            rates.put(currency.getCode(), formatDouble(baseRate / getPlnRate(currency.getCode())));
        }
        return rates;
    }

    public Map<String, Double> getCurrencyRating(Map<String, Double> rates) {
        for (Currency currency : currencyRepository.findAll()) {
            rates.put(currency.getCode().toUpperCase(), getPlnRate(currency.getCode()));
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

}
