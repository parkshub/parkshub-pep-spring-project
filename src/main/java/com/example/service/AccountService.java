package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account newAccount) {
        if (newAccount.getUsername() == "") {
            throw new IllegalArgumentException("Username cannot be blank.");
        }

        if (newAccount.getPassword().length() <= 4) {
            throw new IllegalArgumentException("Password cannot be 4 or less in length.");
        }

        if (accountRepository.findByUsername(newAccount.getUsername()) == null) {
            throw new IllegalArgumentException("User with that username already exists.");
        }


        return accountRepository.save(newAccount);
    }

}
