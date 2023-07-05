package com.example.chatappserver.services;

import com.example.chatappserver.dtos.AccountDTO;
import com.example.chatappserver.models.Account;
import com.example.chatappserver.repositories.AccountRepository;
import com.example.chatappserver.requests.AccountRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> result = new ArrayList<>();
        for (Account account : accounts)
            result.add(modelMapper.map(account, AccountDTO.class));
        return result;
    }

    public AccountDTO getAccountById(Long id) {
        try {
            return modelMapper.map(accountRepository.findById(id), AccountDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public AccountDTO createAccount(AccountRequest accountRequest) {
        Account account = new Account();
        if (accountRepository.findAccountByUsername(accountRequest.getUsername()) != null)
            return null;
        account.setUsername(accountRequest.getUsername());
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO updateAccount(String filePath, Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAvatar(filePath);
        accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }

    public boolean deleteAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        accountRepository.delete(account);
        return true;
    }
}
