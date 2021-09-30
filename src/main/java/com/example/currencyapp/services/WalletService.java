package com.example.currencyapp.services;

import com.example.currencyapp.dto.ExchangeCurrencyDto;
import com.example.currencyapp.exceptions.LackFundsException;
import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.User;
import com.example.currencyapp.model.Wallet;
import com.example.currencyapp.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final CurrencyService currencyService;

    @Lazy
    private final UserService userService;

    public void buyCurrency(ExchangeCurrencyDto exchangeCurrencyDto) {
        User user = userService.findById(exchangeCurrencyDto.getUserId());
        Currency currency = currencyService.getCurrencyByCode(exchangeCurrencyDto.getCurrencyCode());
        Wallet wallet = getUserWallet(user, currency);

        BigDecimal rate = BigDecimal.valueOf(currency.getRate());
        BigDecimal amount = exchangeCurrencyDto.getAmount().multiply(rate);
        BigDecimal newPlnBalance = user.getPlnBalance().subtract(amount);

        if (newPlnBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new LackFundsException();
        }

        user.setPlnBalance(newPlnBalance);
        addBalance(wallet, exchangeCurrencyDto.getAmount());
    }

    public void sellCurrency(ExchangeCurrencyDto exchangeCurrencyDto) {
        User user = userService.findById(exchangeCurrencyDto.getUserId());
        Currency currency = currencyService.getCurrencyByCode(exchangeCurrencyDto.getCurrencyCode());
        Wallet wallet = getUserWallet(user, currency);

        BigDecimal rate = BigDecimal.valueOf(currency.getRate());
        BigDecimal amount = exchangeCurrencyDto.getAmount().multiply(rate);
        BigDecimal newPlnBalance = user.getPlnBalance().add(amount);

        user.setPlnBalance(newPlnBalance);
        subtractBalance(wallet, exchangeCurrencyDto.getAmount());
    }

    public void addBalance(Wallet wallet, BigDecimal amount) {
        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    public void subtractBalance(Wallet wallet, BigDecimal amount) {
        BigDecimal newBalance = wallet.getBalance().subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new LackFundsException();
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    public Wallet createWallet(String currency, User user) {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUser(user);
        wallet.setCurrency(currencyService.getCurrencyByCode(currency));
        walletRepository.save(wallet);
        return wallet;
    }

    public List<Wallet> getAllUserWallets(User user) {
        return walletRepository.findAllByUser(user);
    }

    public Wallet getUserWallet(User user, Currency currency) {
        Optional<Wallet> walletOptional = walletRepository.findByUserAndCurrency(user, currency);
        return walletOptional.orElseGet(() -> createWallet(currency.getCode(), user));
    }

}
