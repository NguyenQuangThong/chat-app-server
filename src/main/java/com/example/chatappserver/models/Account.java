package com.example.chatappserver.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
    String username;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(1000)")
    String password;
    @Column(name = "avatar", columnDefinition = "varchar(1000)")
    String avatar;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participants", cascade = CascadeType.ALL)
    List<Conversation> conversations;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account1", cascade = CascadeType.ALL)
    List<Friend> friend1;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account2", cascade = CascadeType.ALL)
    List<Friend> friend2;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL)
    List<FriendRequest> friendRequests1;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    List<FriendRequest> friendRequests2;
}
