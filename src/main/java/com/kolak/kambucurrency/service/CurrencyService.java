package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.exception.AmountMustBePositiveException;
import com.kolak.kambucurrency.exception.CurrencyNotSupportedException;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";
    private final static int MEDIUM_RATE = 0;

    private final RestTemplate restTemplate;
    private final PersistRequestService persistRequestService;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(RestTemplate restTemplate, PersistRequestService persistRequestService,
                           CurrencyRepository currencyRepository) {
        this.restTemplate = restTemplate;
        this.persistRequestService = persistRequestService;
        this.currencyRepository = currencyRepository;
    }

    public List<String> getAllAvailableCurrencies() {
        persistRequestService.saveRequest();
        return currencyRepository.findAll().stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());
    }

    public List<PersistedRequest> getAllPersistedRequests() {
        return persistRequestService.getAllPersistedRequests();
    }

    public double convert(Double amount, String base, String desired) {
        if (amount <= 0) {
            AmountMustBePositiveException exception = new AmountMustBePositiveException();
            persistRequestService.saveRequestWithError(exception.getMessage());
            throw exception;
        }

        LinkedList<String> invokedExternalApiUrls = new LinkedList<>();
        double baseRate = getPlnRate(base, invokedExternalApiUrls);
        double desiredBase = getPlnRate(desired, invokedExternalApiUrls);
        double converted = formatDouble((baseRate / desiredBase) * amount);

        persistRequestService.saveRequest(amount, base.toUpperCase(), Collections.singletonMap(desired.toUpperCase(), converted), invokedExternalApiUrls);

        return formatDouble(converted);
    }

    public Map<String, Double> getCurrencyRating(String base, List<String> currenciesList) {
        Map<String, Double> rates = new HashMap<>();
        LinkedList<String> invokedExternalApiUrls = new LinkedList<>();

        double baseToPln = getPlnRate(base, invokedExternalApiUrls);
        if (currenciesList.isEmpty()) {
            return getCurrencyRating(base, rates, invokedExternalApiUrls);
        }

        for (String currency : currenciesList) {
            rates.put(currency.toUpperCase(),
                    formatDouble( baseToPln / getPlnRate(currency, invokedExternalApiUrls)));
        }

        persistRequestService.saveRequest(base.toUpperCase(), rates, invokedExternalApiUrls);

        return rates;
    }

    private Map<String, Double> getCurrencyRating(String base, Map<String, Double> rates, List<String> invokedExternalApiUrls) {
        double gbpPlnRate = getPlnRate(base, invokedExternalApiUrls);
        for (Currency currency : currencyRepository.findAll()) {
            rates.put(currency.getCode().toUpperCase(),
                    formatDouble( gbpPlnRate / getPlnRate(currency.getCode(), invokedExternalApiUrls)));
        }
        persistRequestService.saveRequest(base.toUpperCase(), rates, invokedExternalApiUrls);

        return rates;
    }


    private double getPlnRate(String base, List<String> invokedExternalApiUrls) {
        if (repositoryDoesntContainCurrency(base)) {
            CurrencyNotSupportedException exception = new CurrencyNotSupportedException(base);
            persistRequestService.saveRequestWithError(exception.getMessage());
            throw exception;
        }

        if (base.toUpperCase().equals("PLN")) {
            return 1.0d;
        }

        CurrencyDetails currencyDetails =
                restTemplate.getForObject(CURRENCY_API_URL + base + JSON_FORMAT, CurrencyDetails.class);

        double baseCurrencyMidRate = 0d;

        if (currencyDetails != null){
            invokedExternalApiUrls.add(CURRENCY_API_URL + base + JSON_FORMAT);
            return currencyDetails.getRates().get(MEDIUM_RATE).getMid();
        }

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

}
