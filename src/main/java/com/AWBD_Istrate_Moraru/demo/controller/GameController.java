package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/games")
public class GameController {
    private GameService gameService;
    private GenreService genreService;
    private FriendshipService friendshipService;
    private PublisherService publisherService;
    private ReviewService reviewService;

    public GameController(GameService gameService, GenreService genreService, PublisherService publisherService, ReviewService reviewService, FriendshipService friendshipService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.publisherService = publisherService;
        this.reviewService = reviewService;
        this.friendshipService = friendshipService;
    }

    @RequestMapping({""})
    public String getMoviePage(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               Principal principal) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<GameDto> gamePage = gameService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("gamePage", gamePage);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        // Get latest 5 friends chats
        if (principal != null) {
            List<FriendshipDto> recentFriends = friendshipService
                    .getAllAcceptedFriendships(principal.getName())
                    .stream().limit(5).sorted().toList(); // or .sorted() based on recent messages
            model.addAttribute("recentFriends", recentFriends);
        }

        return "gameList";
    }

    @RequestMapping("/{id}")
    public String gameShow(@PathVariable Long id, Model model, Principal principal) {
        GameDto gameDto = gameService.findById(id);
        model.addAttribute("gameDto", gameDto);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        List<ReviewDto> reviewDtos = reviewService.findAllByGameId(id);
        model.addAttribute("reviewDtos", reviewDtos);

        // Get latest 5 friends chats
        if (principal != null) {
            List<FriendshipDto> recentFriends = friendshipService
                    .getAllAcceptedFriendships(principal.getName())
                    .stream().limit(5).sorted().toList(); // or .sorted() based on recent messages
            model.addAttribute("recentFriends", recentFriends);
        }
        
        return "gameShow";
    }

    @RequestMapping("/new")
    public String gameForm(Model model) {
        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        List<PublisherDto> publisherDtos = publisherService.findAll();
        model.addAttribute("publisherDtos", publisherDtos);

        model.addAttribute("gameDto", new GameDto());
        return "gameForm";
    }

    @PostMapping("/new")
    public String gameForm(@ModelAttribute GameDto gameDto) {
        gameDto.setGenres(genreService.findAllByIds(gameDto.getGenreIds()));

        if (gameDto.getPublisher() != null && gameDto.getPublisher().getId() != null) {
            PublisherDto publisher = publisherService.findById(gameDto.getPublisher().getId());
            gameDto.setPublisher(publisher);
        }

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

        List<PublisherDto> publisherDtos = publisherService.findAll();
        model.addAttribute("publisherDtos", publisherDtos);

        return "gameForm";
    }

    @PostMapping("/edit/{id}")
    public String updateGame(@PathVariable Long id, @ModelAttribute GameDto gameDto) {
        gameDto.setId(id);
        gameDto.setGenres(genreService.findAllByIds(gameDto.getGenreIds()));

        gameDto.setId(id);
        gameDto.setGenres(genreService.findAllByIds(gameDto.getGenreIds()));

        if (gameDto.getPublisher() != null && gameDto.getPublisher().getId() != null) {
            PublisherDto publisher = publisherService.findById(gameDto.getPublisher().getId());
            gameDto.setPublisher(publisher);
        }

        gameService.save(gameDto);
        return "redirect:/games";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        gameService.deleteById(id);
        return "redirect:/games";
    }
}