package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.exception.AmountMustBePositiveException;
import com.kolak.kambucurrency.exception.CurrencyNotSupportedException;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;
import com.kolak.kambucurrency.model.nbpapi.Rate;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";

    @Mock CurrencyRepository currencyRepository;

    @Mock RestTemplate restTemplate;


    @InjectMocks
    CurrencyService currencyService;

    @Test
    public void shouldConvert() {
        // given
        final double gbpRate = 5.0;
        final double amount = 10.0;

        when(currencyRepository.findAll())
                .thenReturn(createCurrenciesList());

        doReturn(new CurrencyDetails("GBP", Collections.singletonList(new Rate(gbpRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "GBP" + JSON_FORMAT, CurrencyDetails.class);

        // when
        double convert = currencyService.convert(amount, "GBP", "PLN");
        double expected = gbpRate * amount;

        // then
        Assert.assertEquals(expected, convert, 0.0);
    }

    @Test
    public void shouldConvert1() {
        // given
        double eurRate = 1.5;
        double audRate = 1.4;
        double amount = 4.0;

        when(currencyRepository.findAll())
                .thenReturn(createCurrenciesList());

        doReturn(new CurrencyDetails("EUR", Collections.singletonList(new Rate(eurRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "EUR" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("AUD", Collections.singletonList(new Rate(audRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "AUD" + JSON_FORMAT, CurrencyDetails.class);

        // when
        double convert = currencyService.convert(amount, "EUR", "AUD");
        double expected = formatDouble((eurRate / audRate) * amount);

        // then
        Assert.assertEquals(expected, convert, 0.0);
    }

    @Test()
    public void shouldThrowAmountMustBePositiveException() {
        double amount = 0;
        // then

        Assert.assertThrows(AmountMustBePositiveException.class,
                () -> currencyService.convert(amount, "EUR", "AUD"));

    }

    @Test()
    public void shouldThrowAmountMustBePositiveException1() {
        double amount = -1;
        // then

        Assert.assertThrows(AmountMustBePositiveException.class,
                () -> currencyService.convert(amount, "EUR", "AUD"));

    }

    @Test
    public void shouldReturnCurrencyRatingForTwoOtherCurrencies() {
        // given
        String base = "GBP";
        List<String> currenciesList = Arrays.asList("EUR", "AUD");
        double eurRate = 1.5;
        double audRate = 1.4;
        double gbpRate = 1.1;

        when(currencyRepository.findAll()).thenReturn(createCurrenciesList());

        doReturn(new CurrencyDetails("GBP", Collections.singletonList(new Rate(gbpRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "GBP" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("EUR", Collections.singletonList(new Rate(eurRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "EUR" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("AUD", Collections.singletonList(new Rate(audRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "AUD" + JSON_FORMAT, CurrencyDetails.class);

        // when
        Map<String, Double> currencyRating = currencyService.getCurrencyRating(base, currenciesList);

        // then
        double expectedGbpToEur = formatDouble(gbpRate / eurRate);
        double expectedGbpToAud = formatDouble(gbpRate / audRate);

        Assert.assertFalse(currencyRating.isEmpty());
        Assert.assertEquals(currenciesList.size(), currencyRating.size());
        Assert.assertEquals(expectedGbpToEur, currencyRating.get("EUR"), 0.0);
        Assert.assertEquals(expectedGbpToAud, currencyRating.get("AUD"), 0.0);
    }

    @Test
    public void shouldReturnCurrencyRatingForAllOtherCurrencies() {
        // given
        String base = "GBP";

        List<String> currenciesList = Lists.emptyList();
        double eurRate = 1.5;
        double audRate = 1.4;
        double gbpRate = 1.0;
        double jpyRate = 0.2;
        double hufRate = 0.1;

        when(currencyRepository.findAll()).thenReturn(createCurrenciesList());

        doReturn(new CurrencyDetails("GBP", Collections.singletonList(new Rate(gbpRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "GBP" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("EUR", Collections.singletonList(new Rate(eurRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "EUR" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("AUD", Collections.singletonList(new Rate(audRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "AUD" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("JPY", Collections.singletonList(new Rate(jpyRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "JPY" + JSON_FORMAT, CurrencyDetails.class);

        doReturn(new CurrencyDetails("HUF", Collections.singletonList(new Rate(hufRate))))
                .when(restTemplate).getForObject(CURRENCY_API_URL + "HUF" + JSON_FORMAT, CurrencyDetails.class);
        // when
        Map<String, Double> currencyRating = currencyService.getCurrencyRating(base, currenciesList);

        // then
        double expectedGbpToEur = formatDouble(gbpRate / eurRate);
        double expectedGbpToGbp = formatDouble(gbpRate / gbpRate);
        double expectedGbpToAud = formatDouble(gbpRate / audRate);
        double expectedGbpToJpy = formatDouble(gbpRate / jpyRate);
        double expectedGbpToHuf = formatDouble(gbpRate / hufRate);

        Assert.assertEquals(expectedGbpToEur, currencyRating.get("EUR"), 0.0);
        Assert.assertEquals(expectedGbpToGbp, currencyRating.get("GBP"), 0.0);
        Assert.assertEquals(expectedGbpToAud, currencyRating.get("AUD"), 0.0);
        Assert.assertEquals(expectedGbpToJpy, currencyRating.get("JPY"), 0.0);
        Assert.assertEquals(expectedGbpToHuf, currencyRating.get("HUF"), 0.0);

        Assert.assertFalse(currencyRating.isEmpty());
        Assert.assertEquals(createCurrenciesList().size(), currencyRating.size());

    }

    @Test
    public void shouldThrowCurrencyNotSupportedException() {
        // given
        String base = "GBPD";
        List<String> currenciesList = Arrays.asList("EUR", "AUD");

        // then
        Assert.assertThrows(CurrencyNotSupportedException.class,
                () -> currencyService.getCurrencyRating(base, currenciesList));

    }


    private static Double formatDouble(double toFormat) {
        DecimalFormat df = new DecimalFormat("####.##");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(df.format(toFormat));
    }

    public List<Currency> createCurrenciesList() {
        return Arrays.asList(
                new Currency("EUR"),
                new Currency("GBP"),
                new Currency("PLN"),
                new Currency("JPY"),
                new Currency("HUF"),
                new Currency("AUD")
        );
    }

}
