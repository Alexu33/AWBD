package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.service.*;
import com.AWBD_Istrate_Moraru.demo.utils.ControllerReusable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;
    private GameService gameService;
    private GenreService genreService;
    private FriendshipService friendshipService;
    private UserService userService;
    private ChatMessageService chatMessageService;

    private ControllerReusable controllerReusable;

    public PublisherController(PublisherService publisherService, GameService gameService, GenreService genreService, FriendshipService friendshipService, UserService userService, ChatMessageService chatMessageService) {
        this.publisherService = publisherService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.chatMessageService = chatMessageService;

        this.controllerReusable = new ControllerReusable(userService, friendshipService, chatMessageService);
    }

    @PostMapping("")
    public String createOrUpdatePublisher(@ModelAttribute PublisherDto publisherDto) {
        publisherService.save(publisherDto);

        return "redirect:/publishers";
    }

    @RequestMapping("")
    public String publisherList(Model model, Principal principal) {
        List<PublisherDto> publisherDtos = publisherService.findAll();
        log.info("Publisher List: {}", publisherDtos.size());
        model.addAttribute("publisherDtos", publisherDtos);

        controllerReusable.addFriendsAttributes(model, principal);

        return "publisherList";
    }

    @RequestMapping("/{id}")
    public String publisherShow(@PathVariable Long id, Model model, Principal principal) {

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        PublisherDto publisherDto = publisherService.findById(id);
        model.addAttribute("publisherDto", publisherDto);

        List<GameDto> gameDtos = gameService.findAllByPublisherId(publisherDto.getId());
        log.info("Game List: {}", gameDtos.size());
        model.addAttribute("gameDtos", gameDtos);

        controllerReusable.addFriendsAttributes(model, principal);

        return "publisherShow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PublisherDto publisherDto = publisherService.findById(id);
        model.addAttribute("publisherDto", publisherDto);

        List<PublisherDto> publisherDtos = publisherService.findAll();
        model.addAttribute("publisherDtos", publisherDtos );

        return "publisherForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        publisherService.deleteById(id);
        return "redirect:/publishers";
    }


    @RequestMapping("/form")
    public String publisherForm(Model model) {
        model.addAttribute("publisherDto", new PublisherDto());
        return "publisherForm";
    }

}