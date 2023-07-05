package com.example.chatappserver.controllers;

import com.example.chatappserver.dtos.AccountDTO;
import com.example.chatappserver.requests.AccountRequest;
import com.example.chatappserver.services.AccountService;
import com.example.chatappserver.services.FirebaseService;
import com.google.cloud.storage.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    FirebaseService firebaseService;

    @GetMapping("")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<String> getAvatarByAccountId(@PathVariable Long id) {
        String avatar = accountService.getAccountById(id).getAvatar();
        return avatar != null ? new ResponseEntity<>(avatar, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest) != null ? new ResponseEntity<>(accountService.createAccount(accountRequest), HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        Blob blob = null;
        String fileName = avatar.getOriginalFilename();
        if (!avatar.isEmpty()) {
            File file = firebaseService.multipartToFile(avatar, fileName);
            blob = firebaseService.saveFileToCloud(file, fileName);
            boolean delete = file.delete();
        }
        String filePath = firebaseService.getDownloadURL(fileName);
        AccountDTO result = accountService.updateAccount(blob != null ? filePath : null, id);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable Long id) {
        Boolean result = accountService.deleteAccount(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
