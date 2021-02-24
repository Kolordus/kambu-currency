package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.dto.PersistedRequestDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CurrencyServiceTest {


    @Test
    public void should_return_persisted_requests() {
        //given
        CurrencyService currencyService = mock(CurrencyService.class);
        long expected = 3;

        //when
        when(currencyService.getAllPersistedRequests())
                .thenReturn(createMockPersistedRequestList());

        //then
        Assert.assertEquals(expected, currencyService.getAllPersistedRequests().size());
    }

    @Test
    public void should_convert() {
        //given
        CurrencyService currencyService = mock(CurrencyService.class);
        double expected = 450.0;

        //when
        when(currencyService.convert(100.0, "PLN", "GPB"))
                .thenReturn(450.0);

        //then
//        Assert.assertEquals(expected, currencyService.convert(100.0, "PLN", "GPB"));

    }

    @Test
    public void should_throw_amount_exception() {
        //given
        CurrencyService currencyService = mock(CurrencyService.class);

        //when
        double convert = currencyService.convert(0.0, "PLN", "GPB");

        //then
    }



    private List<PersistedRequestDto> createMockPersistedRequestList() {

        HashMap<String, Double> returnedCurrenciesValues = new HashMap<>();
        returnedCurrenciesValues.put("AUD", 50.0);
        returnedCurrenciesValues.put("GBP", 60.0);
        returnedCurrenciesValues.put("EUR", 70.0);

        return Arrays.asList(
                new PersistedRequestDto(100.0, "PLN", returnedCurrenciesValues, LocalDateTime.now()),
                new PersistedRequestDto(1.0, "PLN", returnedCurrenciesValues, LocalDateTime.now()),
                new PersistedRequestDto(10.0, "PLN", returnedCurrenciesValues, LocalDateTime.now())
        );
    }



}