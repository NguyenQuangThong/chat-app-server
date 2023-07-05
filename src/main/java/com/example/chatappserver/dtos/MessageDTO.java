package com.example.chatappserver.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    Long id;
    String content;
    Date time;
    ConversationDTO conversation;
    AccountDTO sender;
}
