package com.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "password_confirm")
    private String passwordConfirm;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "social_links")
    private String socialLinks;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "users")
    @ToString.Exclude
    private List<ChatRoom> room;

}
