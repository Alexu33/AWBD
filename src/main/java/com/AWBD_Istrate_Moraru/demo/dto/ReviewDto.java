package com.AWBD_Istrate_Moraru.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long id;
    private UserDto user;
    private GameDto game;
    private Long rating;
    private String comment;
    private LocalDate createdAt;
}
