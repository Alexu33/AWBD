package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.entity.Friendship;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.mapper.FriendshipMapper;
import com.AWBD_Istrate_Moraru.demo.repository.FriendshipRepository;
import com.AWBD_Istrate_Moraru.demo.repository.UserRepository;
import com.AWBD_Istrate_Moraru.demo.utils.FriendshipStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @Override
    public void sendFriendRequest(String senderUsername, Long receiverId) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        if (friendshipRepository.existsBySenderAndReceiver(sender, receiver) || friendshipRepository.existsBySenderAndReceiver(receiver, sender)) {
            throw new IllegalStateException("Friendship already exists or pending");
        }

        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendship.setRequestedAt(LocalDateTime.now());

        friendshipRepository.save(friendship);
    }

    @Override
    public void acceptFriendRequest(String receiverUsername, Long requestId) {
        Friendship friendship = getAndValidateRequest(receiverUsername, requestId, FriendshipStatus.PENDING);
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);
    }

    @Override
    public void declineFriendRequest(String receiverUsername, Long requestId) {
        Friendship friendship = getAndValidateRequest(receiverUsername, requestId, FriendshipStatus.PENDING);
        friendshipRepository.delete(friendship);
    }

    @Override
    public void blockFriendRequest(String receiverUsername, Long requestId) {
        Friendship friendship = getAndValidateRequest(receiverUsername, requestId, FriendshipStatus.PENDING);
        friendship.setStatus(FriendshipStatus.BLOCKED);
        User originalReceiver = friendship.getReceiver();
        friendship.setReceiver(friendship.getSender());
        friendship.setSender(originalReceiver);
        friendshipRepository.save(friendship);
    }

    @Override
    public void blockUser(String requesterUsername, Long userId) {
        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User other = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Friendship friendship = friendshipRepository.findByUsers(requester,other).orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
        friendship.setSender(requester);
        friendship.setReceiver(other);
        friendship.setStatus(FriendshipStatus.BLOCKED);
        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void removeFriend(String requesterUsername, Long userId) {
        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User other = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        friendshipRepository.deleteByUsers(requester, other);
    }

    @Override
    public List<FriendshipDto> getAllFriendships(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Friendship> friendships = friendshipRepository.findAllBySenderOrReceiver(user,user);
        return friendships.stream().map(friendshipMapper::toFriendshipDto).toList();
    }

    @Override
    public List<FriendshipDto> getAllAcceptedFriendships(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Friendship> friendships = friendshipRepository.findAllAcceptedFriendships(user, FriendshipStatus.ACCEPTED);
        return friendships.stream().map(friendshipMapper::toFriendshipDto).toList();
    }

    @Override
    public void sendFriendRequestByUsername(String senderUsername, String receiverUsername) {
        if(senderUsername.equals(receiverUsername)) {
            throw new AccessDeniedException("You are not allowed to send yourself");
        }

        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        if (friendshipRepository.existsBySenderAndReceiver(sender, receiver) || friendshipRepository.existsBySenderAndReceiver(receiver, sender)) {
            throw new IllegalStateException("Friendship already exists or pending");
        }

        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendship.setRequestedAt(LocalDateTime.now());

        friendshipRepository.save(friendship);
    }

    @Override
    public List<FriendshipDto> getIncomingPendingRequests(String username) {
        log("do i enter getIncomingPendingRequests");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return friendshipRepository.findAllByReceiverAndStatus(user, FriendshipStatus.PENDING)
                .stream()
                .map(friendshipMapper::toFriendshipDto)
                .toList();
    }

    @Override
    public List<FriendshipDto> getAllBlockedFriendships(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // get all friendships and filter by blocked status.
        return friendshipRepository.findAllBySender(user).stream()
                .filter(friendship -> friendship.getStatus().equals(FriendshipStatus.BLOCKED))
                .map(friendshipMapper::toFriendshipDto)
                .toList();
    }

    private Friendship getAndValidateRequest(String receiverUsername, Long id, FriendshipStatus expectedStatus) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (!friendship.getReceiver().getUsername().equals(receiverUsername) || friendship.getStatus() != expectedStatus) {
            throw new AccessDeniedException("You are not allowed to modify this request");
        }
        return friendship;
    }
}