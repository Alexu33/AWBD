package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.service.*;
import com.AWBD_Istrate_Moraru.demo.utils.ControllerReusable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/genres")
public class GenreController {
    private GenreService genreService;
    private GameService gameService;
    private FriendshipService friendshipService;
    private UserService userService;
    private ChatMessageService chatMessageService;

    private ControllerReusable controllerReusable;

    public GenreController(GenreService genreService, GameService gameService, FriendshipService friendshipService, UserService userService, ChatMessageService chatMessageService) {
        this.genreService = genreService;
        this.gameService = gameService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.chatMessageService = chatMessageService;

        this.controllerReusable = new ControllerReusable(userService, friendshipService, chatMessageService);
    }

    @PostMapping("")
    public String createOrUpdateGenre(@ModelAttribute GenreDto genreDto) {
        genreService.save(genreDto);

        return "redirect:/genres";
    }

    @RequestMapping("")
    public String genreList(Model model) {
        List<GenreDto> genreDtos = genreService.findAll();
        log.info("Genre List: {}", genreDtos.size());
        model.addAttribute("genreDtos", genreDtos);
        return "genreList";
    }

    @RequestMapping("/{id}")
    public String genreShow(@PathVariable Long id, Model model, Principal principal) {

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        GenreDto genreDto = genreService.findById(id);
        model.addAttribute("genreDto", genreDto);

        List<GameDto> gameDtos = gameService.findAllByGenreId(genreDto.getId());
        log.info("Game List: {}", gameDtos.size());
        model.addAttribute("gameDtos", gameDtos);

        controllerReusable.addFriendsAttributes(model, principal);

        return "genreShow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        GenreDto genreDto = genreService.findById(id);
        model.addAttribute("genreDto", genreDto);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos );

        return "genreForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        genreService.deleteById(id);
        return "redirect:/genres";
    }
}