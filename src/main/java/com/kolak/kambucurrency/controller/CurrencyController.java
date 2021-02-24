package com.kolak.kambucurrency.controller;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    public double convertCurrency(@RequestParam(defaultValue = "1") Double amount,
                                  @RequestParam(defaultValue = "PLN") String base,
                                  @RequestParam(defaultValue = "EUR") String desired) {
        return currencyService.convert(amount, base, desired);
    }

    @GetMapping("/get-rates")
    public ResponseEntity<Map<String, Double>> getCurrenciesRatesForBase(@RequestParam(required = false, defaultValue = "PLN") String base,
                                                                         @RequestParam(required = false, defaultValue = "") String[] currencies) {
        return ResponseEntity.ok(currencyService.getCurrencyRating(base, Arrays.asList(currencies)));
    }

    @GetMapping("/all-requests")
    public List<PersistedRequest> method() {
        return currencyService.elo();
    }


}
