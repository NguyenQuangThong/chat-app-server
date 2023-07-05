package com.example.chatappserver.services;

import com.example.chatappserver.dtos.ConversationDTO;
import com.example.chatappserver.models.Conversation;
import com.example.chatappserver.repositories.ConversationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ConversationDTO> getAllConversations() {
        List<Conversation> conversations = conversationRepository.findAll();
        List<ConversationDTO> result = new ArrayList<>();
        for (Conversation conversation : conversations)
            result.add(modelMapper.map(conversation, ConversationDTO.class));
        return result;
    }

    public ConversationDTO getConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id).get();
        return modelMapper.map(conversation, ConversationDTO.class);
    }

    public ConversationDTO createConversation(ConversationDTO conversationDTO) {
        if (conversationRepository.findConversationByName(conversationDTO.getName()) == null)
            return null;
        Conversation conversation = new Conversation();
        conversation.setName(conversationDTO.getName());
        conversationRepository.save(conversation);
        return modelMapper.map(conversation, ConversationDTO.class);
    }

    public ConversationDTO updateConversation(ConversationDTO conversationDTO, Long id) {
        Conversation conversation = conversationRepository.findById(id).get();
        conversation.setName(conversationDTO.getName());
        conversationRepository.save(conversation);
        return modelMapper.map(conversation, ConversationDTO.class);
    }

    public Boolean deleteConversation(Long id) {
        Conversation conversation = conversationRepository.findById(id).get();
        conversationRepository.findById(id).get();
        conversationRepository.delete(conversation);
        return true;
    }
}
