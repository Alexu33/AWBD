package com.AWBD_Istrate_Moraru.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @ManyToOne
    private Game game;
    private LocalDate purchaseDate;
    private BigDecimal pricePaid;
    private String comment;
}
