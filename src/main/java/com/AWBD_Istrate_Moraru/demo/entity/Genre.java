package com.AWBD_Istrate_Moraru.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Game> games;
}
