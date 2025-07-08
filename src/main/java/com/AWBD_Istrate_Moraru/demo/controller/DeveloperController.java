package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
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
@RequestMapping("/developers")
public class DeveloperController {
    private DeveloperService developerService;
    private GameService gameService;
    private GenreService genreService;
    private FriendshipService friendshipService;
    private UserService userService;
    private ChatMessageService chatMessageService;

    private ControllerReusable controllerReusable;

    public DeveloperController(DeveloperService developerService, GameService gameService, GenreService genreService, FriendshipService friendshipService, UserService userService, ChatMessageService chatMessageService) {
        this.developerService = developerService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.chatMessageService = chatMessageService;

        this.controllerReusable = new ControllerReusable(userService, friendshipService, chatMessageService);
    }

    @PostMapping("")
    public String createOrUpdateDeveloper(@ModelAttribute DeveloperDto developerDto) {
        developerService.save(developerDto);

        return "redirect:/developers";
    }

    @RequestMapping("")
    public String developerList(Model model) {
        List<DeveloperDto> developerDtos = developerService.findAll();
        log.info("Developer List: {}", developerDtos.size());
        model.addAttribute("developerDtos", developerDtos);
        return "developerList";
    }

    @RequestMapping("/{id}")
    public String developerShow(@PathVariable Long id, Model model, Principal principal) {

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        DeveloperDto developerDto = developerService.findById(id);
        model.addAttribute("developerDto", developerDto);

        List<GameDto> gameDtos = gameService.findAllByDeveloperId(developerDto.getId());
        log.info("Game List: {}", gameDtos.size());
        model.addAttribute("gameDtos", gameDtos);

        controllerReusable.addFriendsAttributes(model, principal);

        return "developerShow";
    }


    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        DeveloperDto developerDto = developerService.findById(id);
        model.addAttribute("developerDto", developerDto);

        List<DeveloperDto> developerDtos = developerService.findAll();
        model.addAttribute("developerDtos", developerDtos );

        return "developerForm";
    }

    @RequestMapping("/form")
    public String developerForm(Model model) {
        model.addAttribute("developerDto", new DeveloperDto());
        return "developerForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        developerService.deleteById(id);
        return "redirect:/developers";
    }
}
