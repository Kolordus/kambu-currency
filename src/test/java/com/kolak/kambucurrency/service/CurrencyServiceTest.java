package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.nbpapi.CurrencyDetails;
import com.kolak.kambucurrency.model.nbpapi.Rate;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    private final static String CURRENCY_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private final static String JSON_FORMAT = "?format=json";

    @Mock
    CurrencyRepository currencyRepository;

    @Mock
    PersistedRequestRepository persistedRequestRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CurrencyService currencyService;


    @Test
    public void shouldConvert() {
        // given
        when(currencyRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Currency("PLN"),
                        new Currency("GBP")
                ));

        when(restTemplate.getForObject(CURRENCY_API_URL + "GBP" + JSON_FORMAT, CurrencyDetails.class))
                .thenReturn(new CurrencyDetails("GBP", Collections.singletonList(new Rate(5.0))));
        when(restTemplate.getForObject(CURRENCY_API_URL + "PLN" + JSON_FORMAT, CurrencyDetails.class))
                .thenReturn(new CurrencyDetails("PLN", Collections.singletonList(new Rate(1.0))));

        // when
        double convert = currencyService.convert(100.0, "GBP", "PLN");
        double excepted = 500.0;
        Assert.assertEquals(excepted, convert, 0.0);

    }

    @Test
    public void should_throw_amount_exception() {

    }

}