package com.AWBD_Istrate_Moraru.demo.entity;

import com.AWBD_Istrate_Moraru.demo.utils.Roles;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDate joinDate;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
