package com.example.chatappserver.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account1", nullable = false)
    Account account1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account2", nullable = false)
    Account account2;
}
