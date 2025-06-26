package com.errandGo.backend.service;


import com.errandGo.backend.entities.Account;
import com.errandGo.backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get account by ID
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    // Create or update account
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    // Delete account by ID
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    // Get account by email
    public Optional<Account> getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

}
