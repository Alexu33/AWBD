package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    ReviewDto save(ReviewDto reviewDto);

    ReviewDto findById(Long id);

    List<ReviewDto> findAll();

    List<ReviewDto> findAllByGameId(Long gameId);

    void deleteById(Long id);
}
