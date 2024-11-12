package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;


import com.example.entity.Account;
import com.example.exception.DuplicateException;
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

        if (!accountRepository.findByUsername(newAccount.getUsername()).isEmpty()) {
            throw new DuplicateException("User with that username already exists.");
        }

        return accountRepository.save(newAccount);
    }

    public Account loginAccount(Account account) throws AuthenticationException {
        Account matchedAccount = accountRepository.findByUsername(account.getUsername()).orElse(null);

        // using the same authentication error message for both to avoid givin hints
        if (matchedAccount == null) {
            throw new AuthenticationException("Authentication failed, please try again");
        }

        if (!matchedAccount.getPassword().equals(account.getPassword())) {
            throw new AuthenticationException("Authentication failed, please try again");
        }

        return matchedAccount;
    }

}
