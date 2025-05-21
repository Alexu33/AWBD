package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.PublisherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublisherService {
    PublisherDto save(PublisherDto publisherDto);

    PublisherDto findById(Long id);

    List<PublisherDto> findAll();

    void deleteById(Long id);
}
