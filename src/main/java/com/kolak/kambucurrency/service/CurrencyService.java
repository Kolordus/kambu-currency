package com.kolak.kambucurrency.service;


import com.kolak.kambucurrency.exception.AmountMustBePositiveException;
import com.kolak.kambucurrency.exception.CurrencyNotSupportedException;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
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

    private final RestTemplate restTemplate;
    private final UrlService urlService;
    private final CurrencyRepository currencyRepository;
    private final PersistedRequestRepository persistedRequestRepository;

    @Autowired
    public CurrencyService(UrlService urlService, CurrencyRepository currencyRepository, PersistedRequestRepository persistedRequestRepository) {
        this.urlService = urlService;
        this.currencyRepository = currencyRepository;
        this.persistedRequestRepository = persistedRequestRepository;
        restTemplate = new RestTemplate();
    }

    public List<String> getAllAvailableCurrencies() {
        urlService.saveRequest();
        return currencyRepository.findAll().stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());
    }

    public List<PersistedRequest> getAllPersistedRequests() {
        urlService.saveRequest();
        return persistedRequestRepository.findAll();
    }

    public double convert(Double amount, String base, String desired) {
        if (amount < 1) {
            throw new AmountMustBePositiveException();
        }

        double baseRate = getPlnRate(base);
        double desiredBase = getPlnRate(desired);
        double converted = formatDouble((baseRate / desiredBase) * amount);

        urlService.saveRequest();

        return formatDouble(converted);
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

        urlService.saveRequest();

        return rates;
    }

    public Map<String, Double> getCurrencyRating(String base, Map<String, Double> rates) {
        for (Currency currency : currencyRepository.findAll()) {
            rates.put(currency.getCode().toUpperCase(), formatDouble(getPlnRate(base) / getPlnRate(currency.getCode())));
        }
//        saveToDB(null, base, rates);
        urlService.saveRequest();
        return rates;
    }

    private double getPlnRate(String base) {
        if (repositoryDoesntContainCurrency(base)) {
            throw new CurrencyNotSupportedException(base);
        }


        if (base.toUpperCase().equals("PLN")) {
            return 1.0d;
        }

        CurrencyDetails currencyDetails =
                restTemplate.getForObject(CURRENCY_API_URL + base + JSON_FORMAT, CurrencyDetails.class);


        double baseCurrencyMidRate = 0d;

        if (currencyDetails != null)
            return currencyDetails.getRates().get(MEDIUM_RATE).getMid();

        urlService.saveRequest(CURRENCY_API_URL + base + JSON_FORMAT);

        return baseCurrencyMidRate;
    }

    private boolean repositoryDoesntContainCurrency(String base) {
        return currencyRepository.findAll()
                .stream()
                .noneMatch(currency -> currency.getCode().equals(base.toUpperCase()));
    }

    private static Double formatDouble(double toFormat) {
        DecimalFormat df = new DecimalFormat("####.##");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(df.format(toFormat));
    }

//    private void saveToDB(Double amount, String base, Map<String, Double> desired) {
//        PersistedRequest persistedRequest = new PersistedRequest();
//        persistedRequest.setAmount(amount);
//        persistedRequest.setBase(base.toUpperCase());
//        persistedRequest.setDesiredCurrencies(desired);
//        persistedRequest.setTimeCreated(LocalDateTime.now());
//
//        persistedRequestRepository.save(persistedRequest);
//    }


}
