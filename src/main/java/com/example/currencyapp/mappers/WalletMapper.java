package com.example.currencyapp.mappers;

import com.example.currencyapp.dto.WalletDto;
import com.example.currencyapp.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletDto mapToDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getWalletId());
        walletDto.setCurrencyName(wallet.getCurrency().getName());
        walletDto.setCurrencyCode(wallet.getCurrency().getCode());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }
}
