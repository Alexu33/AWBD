package com.AWBD_Istrate_Moraru.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private GameDto game;
    private LocalDate purchaseDate;
    private BigDecimal pricePaid;
    private String comment;
}
