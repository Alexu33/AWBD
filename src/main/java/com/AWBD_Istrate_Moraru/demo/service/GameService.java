package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GameService {
    GameDto save(GameDto gameDto);

    GameDto findById(Long id);

    List<GameDto> findAll();

    void deleteById(Long id);
}
