package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public GameController(GameService gameService, GenreService genreService, PublisherService publisherService, ReviewService reviewService, FriendshipService friendshipService, ChatMessageService chatMessageService, PurchaseService purchaseService, UserService userService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.publisherService = publisherService;
        this.reviewService = reviewService;
        this.friendshipService = friendshipService;
        this.chatMessageService = chatMessageService;
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @RequestMapping({""})
    public String getGamePage(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               Principal principal) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<GameDto> gamePage = gameService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("gamePage", gamePage);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        // Latest friends by last chat message
        if (principal != null) {
            String currentUsername = principal.getName();
            UserDto currentUser = userService.findByUsername(currentUsername).orElse(null);
            model.addAttribute("currentUserId", currentUser.getId());
            List<FriendshipDto> friendships = friendshipService.getAllAcceptedFriendships(currentUsername);

            // For displaying in dropdown: collect friend usernames
            List<UserDto> friends = friendships.stream()
                    .map(f -> f.getSender().getUsername().equals(currentUsername)
                            ? f.getReceiver()
                            : f.getSender())
                    .collect(Collectors.toList());
            model.addAttribute("friends", friends);

            // Pair each friend with their latest message timestamp
            List<Pair<FriendshipDto, LocalDateTime>> sorted = friendships.stream()
                    .map(friendship -> {
                        Long friendId = friendship.getSender().getUsername().equals(currentUsername)
                                ? friendship.getReceiver().getId()
                                : friendship.getSender().getId();

                        return Pair.of(friendship,
                                chatMessageService.getLastMessageBetween(currentUsername, friendId)
                                        .map(ChatMessageDto::getSentAt)
                                        .orElse(LocalDateTime.MIN)); // fallback if no messages
                    })
                    .sorted((a, b) -> b.getSecond().compareTo(a.getSecond())) // sort descending
                    .limit(5)
                    .toList();

            List<FriendshipDto> recentFriends = sorted.stream()
                    .map(Pair::getFirst)
                    .toList();

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

        // Latest friends by last chat message
        if (principal != null) {
            String currentUsername = principal.getName();
            UserDto currentUser = userService.findByUsername(currentUsername).orElse(null);
            model.addAttribute("currentUserId", currentUser.getId());
            List<FriendshipDto> friendships = friendshipService.getAllAcceptedFriendships(currentUsername);

            // For displaying in dropdown: collect friend usernames
            List<UserDto> friends = friendships.stream()
                    .map(f -> f.getSender().getUsername().equals(currentUsername)
                            ? f.getReceiver()
                            : f.getSender())
                    .collect(Collectors.toList());
            model.addAttribute("friends", friends);

            // Pair each friend with their latest message timestamp
            List<Pair<FriendshipDto, LocalDateTime>> sorted = friendships.stream()
                    .map(friendship -> {
                        Long friendId = friendship.getSender().getUsername().equals(currentUsername)
                                ? friendship.getReceiver().getId()
                                : friendship.getSender().getId();

                        return Pair.of(friendship,
                                chatMessageService.getLastMessageBetween(currentUsername, friendId)
                                        .map(ChatMessageDto::getSentAt)
                                        .orElse(LocalDateTime.MIN)); // fallback if no messages
                    })
                    .sorted((a, b) -> b.getSecond().compareTo(a.getSecond())) // sort descending
                    .limit(5)
                    .toList();

            List<FriendshipDto> recentFriends = sorted.stream()
                    .map(Pair::getFirst)
                    .toList();

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