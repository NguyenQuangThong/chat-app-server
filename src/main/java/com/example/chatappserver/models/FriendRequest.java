package com.example.chatappserver.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    Account receiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    Account sender;
}
