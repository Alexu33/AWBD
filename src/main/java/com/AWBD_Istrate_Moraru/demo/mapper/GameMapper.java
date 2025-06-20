package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {
    @Mapping(target = "genres", ignore = true)
    GameDto toDto(Game game);

    Game toEntity(GameDto gameDto);
}
