package com.example.chatappserver.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(30)")
    String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "participantId", nullable = false)
    List<Account> participants;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conversation", cascade = CascadeType.ALL)
    List<Message> messages;
}
