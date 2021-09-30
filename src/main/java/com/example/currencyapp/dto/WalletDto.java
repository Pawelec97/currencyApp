package com.example.currencyapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    long id;
    String currencyName;
    String currencyCode;
    BigDecimal balance;
}
