package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GameService {
    GameDto save(GameDto gameDto);

    GameDto findById(Long id);

    List<GameDto> findAll();

    List<GameDto> findAllByGenreId(Long genreId);

    List<GameDto> findAllByPublisherId(Long publisherId);

    void deleteById(Long id);
}
