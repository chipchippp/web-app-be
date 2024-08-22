package com.example.webapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String provider;
    @Column(name = "provider_id", length = 50, nullable = false)
    private String providerId;

    @Column(name = "name", length = 150)
    private String name;
    @Column(name = "email", length = 150)
    private String email;
}
