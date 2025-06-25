package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.PublisherDto;
import com.AWBD_Istrate_Moraru.demo.entity.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDto toPublisherDto(Publisher publisher);
    Publisher toPublisher(PublisherDto publisherDto);
}
