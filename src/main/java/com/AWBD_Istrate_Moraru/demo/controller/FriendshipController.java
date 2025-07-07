package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.service.*;
import com.AWBD_Istrate_Moraru.demo.utils.ControllerReusable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/friendships")
public class FriendshipController {
    private GenreService genreService;
    private UserService userService;
    private FriendshipService friendshipService;
    private ChatMessageService chatMessageService;

    private ControllerReusable controllerReusable;

    public FriendshipController(GenreService genreService, UserService userService, FriendshipService friendshipService, ChatMessageService chatMessageService) {
        this.genreService = genreService;
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.chatMessageService = chatMessageService;

        this.controllerReusable = new ControllerReusable(userService, friendshipService, chatMessageService);
    }

    @PostMapping("/request/{receiverId}")
    public String sendFriendRequest(@PathVariable Long receiverId, Principal principal) {
        friendshipService.sendFriendRequest(principal.getName(), receiverId);
        return "redirect:/friendships";
    }

    @PostMapping("/request-by-username")
    public String sendFriendRequestByUsername(@RequestParam String username, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            friendshipService.sendFriendRequestByUsername(principal.getName(), username);
            redirectAttributes.addFlashAttribute("success", "Friend request sent!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "User not found: " + username);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/friendships";
    }

    @PostMapping("/accept/{requestId}")
    public String acceptFriendRequest(@PathVariable Long requestId, Principal principal) {
        friendshipService.acceptFriendRequest(principal.getName(), requestId);
        return "redirect:/friendships";
    }

    @PostMapping("/decline/{requestId}")
    public String declineFriendRequest(@PathVariable Long requestId, Principal principal) {
        friendshipService.declineFriendRequest(principal.getName(), requestId);
        return "redirect:/friendships";
    }

    @PostMapping("/block-request/{requestId}")
    public String blockFriendRequest(@PathVariable Long requestId, Principal principal) {
        friendshipService.blockFriendRequest(principal.getName(), requestId);
        return "redirect:/friendships";
    }

    @PostMapping("/block/{userId}")
    public String blockUser(@PathVariable Long userId, Principal principal) {
        friendshipService.blockUser(principal.getName(), userId);
        return "redirect:/friendships";
    }

    @PostMapping("/remove/{userId}")
    public String removeFriend(@PathVariable Long userId, Principal principal) {
        friendshipService.removeFriend(principal.getName(), userId);
        return "redirect:/friendships";
    }

    @GetMapping
    public String getAllFriendships(Model model, Principal principal) {
        List<FriendshipDto> friendships = friendshipService.getAllAcceptedFriendships(principal.getName());
        List<FriendshipDto> pendingRequests = friendshipService.getIncomingPendingRequests(principal.getName());
        List<FriendshipDto> blocked = friendshipService.getAllBlockedFriendships(principal.getName());

        model.addAttribute("friendships", friendships);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("blockedUsers", blocked);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos);

        controllerReusable.addFriendsAttributes(model, principal);

        return "friendsList";
    }
}
