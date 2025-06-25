package com.AWBD_Istrate_Moraru.demo.services;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.mapper.GameMapper;
import com.AWBD_Istrate_Moraru.demo.repository.GameRepository;
import com.AWBD_Istrate_Moraru.demo.service.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    GameMapper gameMapper;
    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameServiceImpl gameService;

    @Test
    public void findGames() {
        List<Game> gameList = new ArrayList<Game>();
        Game game = new Game();
        gameList.add(game);

        when(gameRepository.findAll()).thenReturn(gameList);
        List<GameDto> gameDtos = gameService.findAll();
        assertEquals(gameDtos.size(), 1);
        verify(gameRepository, times(1)).findAll();
    }
}