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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testFindById_Success() {
        Game game = new Game();
        game.setId(1L);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(new GameDto());

        GameDto dto = gameService.findById(1L);
        assertNotNull(dto);
        verify(gameRepository).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> gameService.findById(99L));
    }

    @Test
    public void testSave() {
        GameDto dto = new GameDto();
        Game game = new Game();
        when(gameMapper.toEntity(dto)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(dto);

        GameDto savedDto = gameService.save(dto);
        assertEquals(dto, savedDto);
    }

    @Test
    public void testFindAllByGenreId() {
        Game game = new Game();
        when(gameRepository.findByGenres_Id(1L)).thenReturn(List.of(game));
        when(gameMapper.toDto(game)).thenReturn(new GameDto());

        List<GameDto> result = gameService.findAllByGenreId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAllByPublisherId() {
        Game game = new Game();
        when(gameRepository.findByPublisher_Id(1L)).thenReturn(List.of(game));
        when(gameMapper.toDto(game)).thenReturn(new GameDto());

        List<GameDto> result = gameService.findAllByPublisherId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAllByDeveloperId() {
        Game game = new Game();
        when(gameRepository.findByDeveloper_Id(1L)).thenReturn(List.of(game));
        when(gameMapper.toDto(game)).thenReturn(new GameDto());

        List<GameDto> result = gameService.findAllByDeveloperId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteById() {
        gameService.deleteById(5L);
        verify(gameRepository).deleteById(5L);
    }

    @Test
    public void testFindPaginated() {
        Game game = new Game();
        Page<Game> gamePage = new PageImpl<>(List.of(game));
        when(gameRepository.findAll(any(Pageable.class))).thenReturn(gamePage);
        when(gameMapper.toDto(any(Game.class))).thenReturn(new GameDto());

        Page<GameDto> result = gameService.findPaginated(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }
}