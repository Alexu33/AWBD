package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private GameService gameService;
    private GenreService genreService;
    private FriendshipService friendshipService;
    private PurchaseService purchaseService;

    public UserController(UserService userService, GameService gameService, GenreService genreService, FriendshipService friendshipService, PurchaseService purchaseService) {
        this.userService = userService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.friendshipService = friendshipService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("")
    public String createOrUpdateUser(@ModelAttribute UserDto userDto) {
        userService.save(userDto);

        return "redirect:/users/profile";
    }

    @RequestMapping("")
    public String userList(Model model) {
        List<UserDto> userDtos = userService.findAll();
        log.info("User List: {}", userDtos.size());
        model.addAttribute("userDtos", userDtos);
        return "userList";
    }

    @RequestMapping("/{id}")
    public String userShow(@PathVariable Long id, Model model, Principal principal) {
        UserDto userDto = userService.findById(id);
        model.addAttribute("userDto", userDto);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        List<PurchaseDto> purchaseDtos = purchaseService.findAllByUserId(id);
        model.addAttribute("purchaseDtos", purchaseDtos);

        return "userShow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findById(id);
        model.addAttribute("userDto", userDto);

        List<UserDto> userDtos = userService.findAll();
        model.addAttribute("userDtos", userDtos );

        return "userForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("userDto", userDto);

            List<GenreDto> genreDtos = genreService.findAll();
            model.addAttribute("genreDtos", genreDtos);

            return "profile"; // Thymeleaf template name
        }
        return "redirect:/login";
    }

}