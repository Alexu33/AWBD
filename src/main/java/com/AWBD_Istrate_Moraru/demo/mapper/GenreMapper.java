package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "games", ignore = true)
    GenreDto toDto(Genre genre);

    Genre toEntity(GenreDto genreDto);
}
