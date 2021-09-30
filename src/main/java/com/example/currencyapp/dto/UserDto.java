package com.example.currencyapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDto {
    long id;
    String firstName;
    String lastName;
    BigDecimal pln;
    List<WalletDto> wallets;
}
