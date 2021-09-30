package com.example.currencyapp.mappers;

import com.example.currencyapp.dto.UserDto;
import com.example.currencyapp.model.User;
import com.example.currencyapp.model.Wallet;
import com.example.currencyapp.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPln(user.getPlnBalance());
        List<Wallet> wallets = walletService.getAllUserWallets(user);
        userDto.setWallets(wallets.stream()
                                    .map(walletMapper::mapToDto)
                                    .collect(Collectors.toList()));
        return userDto;
    }
}
