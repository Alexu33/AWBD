package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.entity.Friendship;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.mapper.FriendshipMapper;
import com.AWBD_Istrate_Moraru.demo.repository.FriendshipRepository;
import com.AWBD_Istrate_Moraru.demo.repository.UserRepository;
import com.AWBD_Istrate_Moraru.demo.utils.FriendshipStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        if (friendshipRepository.existsBySenderAndReceiver(sender, receiver)) {
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
        friendship.setStatus(FriendshipStatus.DECLINED);
        friendshipRepository.save(friendship);
    }

    @Override
    public void blockUser(String requesterUsername, Long userId) {
        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User other = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Friendship friendship = new Friendship();
        friendship.setSender(requester);
        friendship.setReceiver(other);
        friendship.setStatus(FriendshipStatus.BLOCKED);
        friendship.setRequestedAt(LocalDateTime.now());

        friendshipRepository.save(friendship);
    }

    @Override
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
        List<Friendship> friendships = friendshipRepository.findAllBySenderOrReceiverAndStatus(user,user,FriendshipStatus.ACCEPTED);
        return friendships.stream().map(friendshipMapper::toFriendshipDto).toList();
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