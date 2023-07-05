package com.example.chatappserver.repositories;

import com.example.chatappserver.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findConversationByName(String name);
}
