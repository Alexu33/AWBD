package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDto toDto(Game game);
    Game toEntity(GameDto gameDto);
}
