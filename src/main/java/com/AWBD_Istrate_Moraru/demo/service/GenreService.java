package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GenreService {
    GenreDto save(GenreDto genreDto);

    GenreDto findById(Long id);

    List<GenreDto> findAll();

    List<GenreDto> findAllByIds(List<Long> ids);

    void deleteById(Long id);
}
