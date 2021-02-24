package com.kolak.kambucurrency.controller;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.model.dto.PersistedRequestDto;
import com.kolak.kambucurrency.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/all-available-currencies")
    public ResponseEntity<List<String>> getAllAvailableCurrencies() {
        return ResponseEntity.ok(currencyService.getAllAvailableCurrencies());
    }

    @GetMapping("/all-requests")
    public List<PersistedRequestDto> getAllRequests() {
        return currencyService.getAllPersistedRequests();
    }

    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(@RequestParam(defaultValue = "1") Double amount,
                                  @RequestParam(defaultValue = "PLN") String base,
                                  @RequestParam(defaultValue = "EUR") String desired) {
        return ResponseEntity.ok(currencyService.convert(amount, base, desired));
    }

    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getCurrenciesRatesForBase(@RequestParam(required = false, defaultValue = "PLN") String base,
                                                                         @RequestParam(required = false, defaultValue = "") String[] currencies) {
        return ResponseEntity.ok(currencyService.getCurrencyRating(base, Arrays.asList(currencies)));
    }

}
