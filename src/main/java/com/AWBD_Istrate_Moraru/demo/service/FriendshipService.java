package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;

import java.util.List;

public interface FriendshipService {
    void sendFriendRequest(String senderUsername, Long receiverId);
    void acceptFriendRequest(String receiverUsername, Long requestId);
    void declineFriendRequest(String receiverUsername, Long requestId);
    void blockUser(String requesterUsername, Long userId);
    void removeFriend(String requesterUsername, Long userId);
    List<FriendshipDto> getAllFriendships(String username);
    List<FriendshipDto> getAllAcceptedFriendships(String username);

    void sendFriendRequestByUsername(String senderUsername, String receiverUsername);
    List<FriendshipDto> getIncomingPendingRequests(String name);
}
