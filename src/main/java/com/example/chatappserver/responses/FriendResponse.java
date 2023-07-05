package com.example.chatappserver.responses;

import com.example.chatappserver.dtos.AccountDTO;
import lombok.Data;

@Data
public class FriendResponse {
    Long id;
    AccountDTO account1;
    AccountDTO account2;
}
