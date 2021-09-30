package com.example.currencyapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeCurrencyDto {
    long userId;
    String currencyCode;
    BigDecimal amount;
}
