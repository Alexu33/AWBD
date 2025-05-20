package com.AWBD_Istrate_Moraru.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String website;
}
