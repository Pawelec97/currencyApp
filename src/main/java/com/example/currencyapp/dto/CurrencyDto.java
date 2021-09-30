package com.example.currencyapp.dto;

import lombok.Data;

@Data
public class CurrencyDto {
    long currencyId;
    String name;
    String code;
    Double rate;

}
