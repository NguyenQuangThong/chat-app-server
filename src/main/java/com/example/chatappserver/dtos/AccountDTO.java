package com.example.chatappserver.dtos;

import lombok.Data;

@Data
public class AccountDTO {
    Long id;
    String username;
    String password;
    String avatar;
}
