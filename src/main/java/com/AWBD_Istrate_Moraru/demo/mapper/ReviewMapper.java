package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.ReviewDto;
import com.AWBD_Istrate_Moraru.demo.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review toEntity(ReviewDto dto);
}
