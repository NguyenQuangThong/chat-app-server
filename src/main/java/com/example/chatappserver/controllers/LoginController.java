package com.example.chatappserver.controllers;

import com.example.chatappserver.dtos.LoginDTO;
import com.example.chatappserver.models.Account;
import com.example.chatappserver.repositories.AccountRepository;
import com.example.chatappserver.responses.LoginResponse;
import com.example.chatappserver.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        Boolean auth = loginService.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword());
        Account account = accountRepository.findAccountByUsername(loginDTO.getUsername());
        return auth ? new ResponseEntity<>(new LoginResponse(account.getId(), loginDTO.getUsername(), loginService.generateToken(loginDTO.getUsername(), loginDTO.getPassword())), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
