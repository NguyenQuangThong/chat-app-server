package com.example.chatappserver.controllers;

import com.example.chatappserver.dtos.ConversationDTO;
import com.example.chatappserver.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    @Autowired
    ConversationService conversationService;

    @GetMapping("")
    public ResponseEntity<List<ConversationDTO>> getAllConversations() {
        return new ResponseEntity<>(conversationService.getAllConversations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDTO> getConversationById(@PathVariable Long id) {
        ConversationDTO result = conversationService.getConversationById(id);
        return result == null ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ConversationDTO> createConversation(@RequestBody ConversationDTO conversationDTO) {
        ConversationDTO result = conversationService.createConversation(conversationDTO);
        return result == null ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConversationDTO> updateConversation(@RequestBody ConversationDTO conversationDTO, @PathVariable Long id) {
        ConversationDTO result = conversationService.updateConversation(conversationDTO, id);
        return result == null ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteConversation(@PathVariable Long id) {
        Boolean result = conversationService.deleteConversation(id);
        return result ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
