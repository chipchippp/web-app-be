package com.example.webapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", length = 255)
    private String fullName;
    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;
    @Column(name = "address", length = 255)
    private String address;
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "facebook_account_id")
    private Integer facebookAccountId;
    @Column(name = "google_account_id")
    private Integer googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;
}