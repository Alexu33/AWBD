package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.mapper.GameMapper;
import com.AWBD_Istrate_Moraru.demo.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    GameRepository gameRepository;
    GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }


    @Override
    public GameDto save(GameDto gameDto) {
        Game savedGame = gameRepository.save(gameMapper.toEntity(gameDto));
        return gameMapper.toDto(savedGame);
    }

    @Override
    public GameDto findById(Long id) {
        Optional<Game> gameOpt = gameRepository.findById(id);

        if (gameOpt.isEmpty()) {
            throw new RuntimeException("Game not found");
        }

        return gameMapper.toDto(gameOpt.get());
    }

    @Override
    public List<GameDto> findAll() {
        List<Game> games = gameRepository.findAll();

        return games.stream()
                .map(d -> gameMapper.toDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findAllByGenreId(Long genreId) {
        List<Game> games = gameRepository.findByGenres_Id(genreId);

        return games.stream()
                .map(d -> gameMapper.toDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findAllByPublisherId(Long publisherId) {
        List<Game> games = gameRepository.findByPublisher_Id(publisherId);

        return games.stream()
                .map(d -> gameMapper.toDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }
}
