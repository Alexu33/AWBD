package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.dto.PublisherDto;
import com.AWBD_Istrate_Moraru.demo.service.FriendshipService;
import com.AWBD_Istrate_Moraru.demo.service.GameService;
import com.AWBD_Istrate_Moraru.demo.service.GenreService;
import com.AWBD_Istrate_Moraru.demo.service.PublisherService;
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
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;
    private GameService gameService;
    private GenreService genreService;
    private FriendshipService friendshipService;

    public PublisherController(PublisherService publisherService, GameService gameService, GenreService genreService, FriendshipService friendshipService) {
        this.publisherService = publisherService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.friendshipService = friendshipService;
    }

    @PostMapping("")
    public String createOrUpdatePublisher(@ModelAttribute PublisherDto publisherDto) {
        publisherService.save(publisherDto);

        return "redirect:/publishers";
    }

    @RequestMapping("")
    public String publisherList(Model model) {
        List<PublisherDto> publisherDtos = publisherService.findAll();
        log.info("Publisher List: {}", publisherDtos.size());
        model.addAttribute("publisherDtos", publisherDtos);
        return "publisherList";
    }

    @RequestMapping("/{id}")
    public String genreShow(@PathVariable Long id, Model model, Principal principal) {

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        PublisherDto publisherDto = publisherService.findById(id);
        model.addAttribute("publisherDto", publisherDto);

        List<GameDto> gameDtos = gameService.findAllByPublisherId(publisherDto.getId());
        log.info("Game List: {}", gameDtos.size());
        model.addAttribute("gameDtos", gameDtos);

        // Get latest 5 friends chats
        if (principal != null) {
            List<FriendshipDto> recentFriends = friendshipService
                    .getAllAcceptedFriendships(principal.getName())
                    .stream().limit(5).sorted().toList(); // or .sorted() based on recent messages
            model.addAttribute("recentFriends", recentFriends);
        }

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
}