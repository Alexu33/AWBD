package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.DeveloperDto;
import com.AWBD_Istrate_Moraru.demo.entity.Developer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    DeveloperDto save(DeveloperDto developerDto);

    DeveloperDto findById(Long id);

    List<DeveloperDto> findAll();

    void deleteById(Long id);
}
