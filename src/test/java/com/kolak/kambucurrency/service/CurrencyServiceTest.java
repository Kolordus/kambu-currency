package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.dto.PersistedRequestDto;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


class CurrencyServiceTest {

    @Mock
    CurrencyRepository currencyRepository;

    @Mock
    PersistedRequestRepository persistedRequestRepository;

    @Test
    public void should_return_persisted_requests() {

    }

    @Test
    public void should_convert() {

    }

    @Test
    public void should_throw_amount_exception() {

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