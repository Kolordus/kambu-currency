package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PersistRequestServiceTest {

    @Mock PersistedRequestRepository persistedRequestRepository;

    @InjectMocks
    PersistRequestService persistRequestService;

    @Test
    public void should() {
        String base = "GBP";
        Map<String, Double> desiredCurrencies =  new HashMap<>();
        desiredCurrencies.put("AUD", 3.33);
        desiredCurrencies.put("USD", 2.22);

        List<String> request =
                Collections.singletonList("http://localhost:8080/api/rates?base=gbp&currencies=usd,aud");



        persistRequestService.saveRequest();
    }
}
