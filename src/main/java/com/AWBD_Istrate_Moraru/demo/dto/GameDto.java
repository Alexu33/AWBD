package com.AWBD_Istrate_Moraru.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameDto {
    private Long id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private BigDecimal price;
    private PublisherDto publisher;
    private DeveloperDto developer;
    private List<Long> genreIds;
    private List<GenreDto> genres;
    private List<CartDto> carts;
    private Double averageRating;
}
