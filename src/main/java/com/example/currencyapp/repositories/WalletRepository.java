package com.example.currencyapp.repositories;

import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.User;
import com.example.currencyapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserAndCurrency(User user, Currency currency);

    List<Wallet> findAllByUser(User user);
}
