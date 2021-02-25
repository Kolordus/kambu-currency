//package com.kolak.kambucurrency;
//
//
//import com.kolak.kambucurrency.model.Currency;
//import com.kolak.kambucurrency.repository.CurrencyRepository;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Bootstrap {
//
//    private final CurrencyRepository currencyRepository;
//
//    public Bootstrap(CurrencyRepository currencyRepository) {
//        this.currencyRepository = currencyRepository;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void method() {
//
//        if (currencyRepository.findAll().isEmpty()) {
//            currencyRepository.save(new Currency("GBP"));
//            currencyRepository.save(new Currency("PLN"));
//            currencyRepository.save(new Currency("EUR"));
//            currencyRepository.save(new Currency("JPY"));
//            currencyRepository.save(new Currency("ISK"));
//            currencyRepository.save(new Currency("USD"));
//        }
//
//    }
//}
