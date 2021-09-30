package com.example.currencyapp.controllers;

import com.example.currencyapp.dto.ExchangeCurrencyDto;
import com.example.currencyapp.services.CurrencyService;
import com.example.currencyapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;
    private final WalletService walletService;

    @GetMapping("/{code}")
    public ResponseEntity<Double> getCurrencyRate(@PathVariable("code") String code) {
        Double rate = currencyService.getRate(code);
        return new ResponseEntity<>(rate, HttpStatus.FOUND);
    }

    @PostMapping("/buy")
    public void buyCurrency(@RequestBody ExchangeCurrencyDto exchangeCurrencyDto) {
        walletService.buyCurrency(exchangeCurrencyDto);
    }

    @PostMapping("/sell")
    public void sellCurrency(@RequestBody ExchangeCurrencyDto exchangeCurrencyDto) {
        walletService.sellCurrency(exchangeCurrencyDto);
    }
}
