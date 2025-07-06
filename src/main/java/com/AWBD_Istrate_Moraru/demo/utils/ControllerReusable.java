package com.AWBD_Istrate_Moraru.demo.utils;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerReusable {

    private final UserService userService;
    private final FriendshipService friendshipService;
    private final ChatMessageService chatMessageService;

    public void addFriendsAttributes(Model model, Principal principal) {
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
    }
}
