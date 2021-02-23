package com.kolak.kambucurrency;

import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.repository.CurrencyRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

    private final CurrencyRepository currencyRepository;


    public Bootstrap(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void method() {
        if (currencyRepository.findAll().isEmpty()) {
            Currency currency = new Currency("GBP");
            Currency currency1 = new Currency("EUR");
            Currency currency2 = new Currency("USD");
            Currency currency3 = new Currency("AUD");
            Currency currency4 = new Currency("JPY");
            Currency currency5 = new Currency("ISK");
            Currency currency6 = new Currency("HUF");

            currencyRepository.save(currency);
            currencyRepository.save(currency1);
            currencyRepository.save(currency2);
            currencyRepository.save(currency3);
            currencyRepository.save(currency4);
            currencyRepository.save(currency5);
            currencyRepository.save(currency6);
        }
    }
}
