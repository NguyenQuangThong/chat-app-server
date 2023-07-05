package com.example.chatappserver.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    Long id;
    String username;
    String token;
}
