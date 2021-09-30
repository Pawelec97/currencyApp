package com.example.currencyapp.handlers;

import com.example.currencyapp.exceptions.LackFundsException;
import com.example.currencyapp.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = LackFundsException.class)
    protected ResponseEntity<String> lackFunds() {
        return new ResponseEntity<>("Not enough funds in the account.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WebClientResponseException.class)
    protected ResponseEntity<String> wrongCurrencyCode() {
        return new ResponseEntity<>("Wrong currency code.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<String> userNotFound() {
        return new ResponseEntity<>("User id not found.", HttpStatus.BAD_REQUEST);
    }


}
