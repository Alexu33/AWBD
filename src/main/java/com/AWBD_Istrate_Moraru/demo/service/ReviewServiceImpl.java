package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.ReviewDto;
import com.AWBD_Istrate_Moraru.demo.entity.Review;
import com.AWBD_Istrate_Moraru.demo.mapper.ReviewMapper;
import com.AWBD_Istrate_Moraru.demo.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public ReviewDto save(ReviewDto reviewDto) {
        Review savedReview = reviewRepository.save(reviewMapper.toEntity(reviewDto));
        return reviewMapper.toDto(savedReview);
    }

    @Override
    public ReviewDto findById(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);

        if (reviewOpt.isEmpty()) {
            throw new RuntimeException("Review not found");
        }

        return reviewMapper.toDto(reviewOpt.get());
    }

    @Override
    public List<ReviewDto> findAll() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream()
                .map(d -> reviewMapper.toDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
