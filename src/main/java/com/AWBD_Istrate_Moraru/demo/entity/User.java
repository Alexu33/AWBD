package com.AWBD_Istrate_Moraru.demo.entity;

import com.AWBD_Istrate_Moraru.demo.utils.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
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
