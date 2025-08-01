package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByTitleLike(String title);

    List<Game> findByGenres_Id(Long genreId);

    List<Game> findByPublisher_Id(Long publisherId);

    List<Game> findByDeveloper_Id(Long developerId);
}
