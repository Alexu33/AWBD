package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/games")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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
        return "gameList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        GameDto gameDto = gameService.findById(id);
        model.addAttribute("gameDto", gameDto);

        List<GameDto> gameDtos = gameService.findAll();
        model.addAttribute("gameDtos", gameDtos );

        return "gameForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        gameService.deleteById(id);
        return "redirect:/games";
    }
}