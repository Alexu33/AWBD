package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request/{receiverId}")
    public String sendFriendRequest(@PathVariable Long receiverId, Principal principal) {
        friendshipService.sendFriendRequest(principal.getName(), receiverId);
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
        List<FriendshipDto> friendships = friendshipService.getAllFriendships(principal.getName());
        model.addAttribute("friendships", friendships);
        return "friendships/list";
    }
}
