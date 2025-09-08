package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.service.*;
import com.AWBD_Istrate_Moraru.demo.utils.ControllerReusable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    private ChatMessageService chatMessageService;
    private PurchaseService purchaseService;
    private UserService userService;
    private DeveloperService developerService;

    private ControllerReusable controllerReusable;

    public GameController(GameService gameService, GenreService genreService, PublisherService publisherService, ReviewService reviewService, FriendshipService friendshipService, ChatMessageService chatMessageService, PurchaseService purchaseService, UserService userService, DeveloperService developerService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.publisherService = publisherService;
        this.reviewService = reviewService;
        this.friendshipService = friendshipService;
        this.chatMessageService = chatMessageService;
        this.purchaseService = purchaseService;
        this.userService = userService;
        this.developerService = developerService;

        this.controllerReusable = new ControllerReusable(userService, friendshipService, chatMessageService);
    }

    @RequestMapping({""})
    public String getGamePage(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortCrit") Optional<String> scrit,
                               @RequestParam("sortDir") Optional<String> sdir,
                               Principal principal) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        String sortCrit = scrit.orElse("releaseDate");
        Sort.Direction sortDir = sdir.map(Sort.Direction::fromString)
                                     .orElse(Sort.Direction.ASC);

        Page<GameDto> gamePage = gameService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize,
                Sort.by(sortDir, sortCrit))
        );

        model.addAttribute("gamePage", gamePage);
        model.addAttribute("currentSort", sortCrit);
        model.addAttribute("currentDir", sortDir.toString());

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        controllerReusable.addFriendsAttributes(model, principal);

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

        controllerReusable.addFriendsAttributes(model, principal);
        
        return "gameShow";
    }

    @RequestMapping("/new")
    public String gameForm(Model model) {
        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        List<PublisherDto> publisherDtos = publisherService.findAll();
        model.addAttribute("publisherDtos", publisherDtos);

        List<DeveloperDto> developerDtos = developerService.findAll();
        model.addAttribute("developerDtos", developerDtos);

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

        if (gameDto.getDeveloper() != null && gameDto.getDeveloper().getId() != null) {
            DeveloperDto developer = developerService.findById(gameDto.getDeveloper().getId());
            gameDto.setDeveloper(developer);
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

        List<DeveloperDto> developerDtos = developerService.findAll();
        model.addAttribute("developerDtos", developerDtos);

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

        if (gameDto.getDeveloper() != null && gameDto.getDeveloper().getId() != null) {
            DeveloperDto developer = developerService.findById(gameDto.getDeveloper().getId());
            gameDto.setDeveloper(developer);
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