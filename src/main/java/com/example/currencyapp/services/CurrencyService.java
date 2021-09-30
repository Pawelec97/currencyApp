package com.example.currencyapp.services;

import com.example.currencyapp.model.Currency;
import com.example.currencyapp.repositories.CurrencyRepository;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Service
public class CurrencyService {

    WebClient client;
    CurrencyRepository currencyRepository;

    @Autowired
    CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        client = WebClient.create("http://api.nbp.pl/api/");
    }

    public Currency getCurrencyByCode(String code) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(code.toUpperCase());
        return currencyOptional.orElseGet(() -> addNewCurrency(code));
    }

    public Double getRate(String code) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(code.toUpperCase());
        Currency currency = currencyOptional.orElseGet(() -> addNewCurrency(code));
        return updateRate(currency);
    }

    public Currency addNewCurrency(String currencyCode) {
        String currencyInfo = getCurrencyInfo(currencyCode);
        Currency currency = new Currency();
        currency.setCode(currencyCode.toUpperCase());
        currency.setName(JsonPath.parse(currencyInfo).read("$['currency']", String.class));
        currency.setRate(JsonPath.parse(currencyInfo).read("$['rates'].[0].['mid']", Double.class));
        currencyRepository.save(currency);
        return currency;
    }

    public Double updateRate(Currency currency) {
        String currencyInfo = getCurrencyInfo(currency.getCode());

        currency.setRate(JsonPath.parse(currencyInfo).read("$['rates'].[0].['mid']", Double.class));
        currencyRepository.save(currency);
        return currency.getRate();
    }

    private String getCurrencyInfo(String currencyCode) {
        return client.get()
                .uri("/exchangerates/rates/a/" + currencyCode)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
