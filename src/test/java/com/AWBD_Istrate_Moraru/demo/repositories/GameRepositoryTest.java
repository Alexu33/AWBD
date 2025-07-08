package com.AWBD_Istrate_Moraru.demo.repositories;

import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Slf4j
public class GameRepositoryTest {
    GameRepository gameRepository;
    @Autowired
    GameRepositoryTest(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Test
    public void findGamesByPublisherId() {
        List<Game> games = gameRepository.findByPublisher_Id(1L);
        assertTrue(games.size() >= 1);
        log.info("findByPublisher_Id ...");
        games.forEach(game -> log.info(game.getTitle()));
    }

    @Test
    public void findGamesByGenreId() {
        List<Game> games = gameRepository.findByGenres_Id(1L);
        assertTrue(games.size() >= 1);
        log.info("findByGenres_Id ...");
        games.forEach(game -> log.info(game.getTitle()));
    }

    @Test
    public void findGames() {
        List<Game> games = gameRepository.findAll();
        assertTrue(games.size() >= 1);
        log.info("findAll ...");
        games.forEach(game -> log.info(game.getTitle()));
    }

}