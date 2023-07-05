package com.example.chatappserver.services;

import com.example.chatappserver.configs.JwtUtils;

import com.example.chatappserver.models.Account;
import com.example.chatappserver.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean authenticateUser(String username, String password) {
        Account account = accountRepository.findAccountByUsername(username);
        return account != null && passwordEncoder.matches(password, account.getPassword());
    }

    public String generateToken(String username, String password) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(username, password);
        return jwtUtils.createToken(claims, username);
    }
}
