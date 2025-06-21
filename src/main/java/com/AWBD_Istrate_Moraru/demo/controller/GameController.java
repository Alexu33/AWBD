package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.service.GameService;
import com.AWBD_Istrate_Moraru.demo.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/games")
public class GameController {
    private GameService gameService;
    private GenreService genreService;

    public GameController(GameService gameService, GenreService genreService) {
        this.gameService = gameService;
        this.genreService = genreService;
    }

    @PostMapping("")
    public String createOrUpdateGame(@ModelAttribute GameDto gameDto) {
        gameService.save(gameDto);

        return "redirect:/games";
    }

    @RequestMapping("")
    public String gameList(Model model) {
        List<GameDto> gameDtos = gameService.findAll();
        log.info("Game List: {}", gameDtos.size());
        model.addAttribute("gameDtos", gameDtos);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        return "gameList";
    }

    @RequestMapping("/new")
    public String gameForm(Model model) {
        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        model.addAttribute("gameDto", new GameDto());
        return "gameForm";
    }

    @PostMapping("/new")
    public String gameForm(@ModelAttribute GameDto gameDto) {
        gameDto.setGenres(genreService.findAllByIds(gameDto.getGenreIds()));

        gameService.save(gameDto);
        return "redirect:/games";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        GameDto gameDto = gameService.findById(id);

        List<Long> genreIds = gameDto.getGenres()
                                     .stream()
                                     .map(GenreDto::getId)
                                     .collect(Collectors.toList());
        gameDto.setGenreIds(genreIds);

        model.addAttribute("gameDto", gameDto);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        return "gameForm";
    }

    @PostMapping("/edit/{id}")
    public String updateGame(@PathVariable Long id, @ModelAttribute GameDto gameDto) {
        gameDto.setId(id);
        gameDto.setGenres(genreService.findAllByIds(gameDto.getGenreIds()));

        List<Long> genreIds = gameDto.getGenres()
                                     .stream()
                                     .map(GenreDto::getId)
                                     .collect(Collectors.toList());
        gameDto.setGenreIds(genreIds);

        gameService.save(gameDto);
        return "redirect:/games";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        gameService.deleteById(id);
        return "redirect:/games";
    }
}