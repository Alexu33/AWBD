package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genre genre);
    Genre toEntity(GenreDto genreDto);
}
