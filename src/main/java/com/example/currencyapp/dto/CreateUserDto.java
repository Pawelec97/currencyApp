package com.example.currencyapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateUserDto {
    String firstName;
    String lastName;
    BigDecimal startBalance;
}
