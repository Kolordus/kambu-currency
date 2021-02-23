package com.kolak.kambucurrency.controller;


import com.kolak.kambucurrency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/test")
    public String method() {
        return "test endpoint";
    }

    @PostMapping("/post-test")
    public void postTest(@RequestParam String elo) {
        System.out.println(elo);
    }

    @GetMapping("/get-rating")
    public Map<String, Double> getCurrencyRating(@RequestParam String[] params) {
        Map<String, Double> currencyRating = currencyService.getCurrencyRating(Arrays.asList(params));
        return currencyRating;
    }
}
