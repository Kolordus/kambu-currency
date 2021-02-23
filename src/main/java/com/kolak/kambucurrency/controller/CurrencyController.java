package com.kolak.kambucurrency.controller;


import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/get-all-available")
    public ResponseEntity<List<String>> getAllAvailableCurrencies() {
        return ResponseEntity.ok(currencyService.getAllAvailableCurrencies());
    }

    @GetMapping("/convert")
    public double convertCurrency(@RequestParam Double amount, @RequestParam String base, @RequestParam String desired) {
        return currencyService.convert(amount, base, desired);
    }

    @GetMapping("/get-rating")
    public ResponseEntity<Map<String, Double>> getCurrenciesRatesForBase(@RequestParam(required = false) String base,
                                                                         @RequestParam(required = false, defaultValue = "") String[] currencies) {
        return ResponseEntity.ok(currencyService.getCurrencyRating(base, Arrays.asList(currencies)));
    }


}
