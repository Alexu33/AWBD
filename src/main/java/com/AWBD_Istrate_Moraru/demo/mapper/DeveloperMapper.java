package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.DeveloperDto;
import com.AWBD_Istrate_Moraru.demo.entity.Developer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    DeveloperDto toDeveloperDto(Developer developer);
    Developer toDeveloper(DeveloperDto developerDto);
}
