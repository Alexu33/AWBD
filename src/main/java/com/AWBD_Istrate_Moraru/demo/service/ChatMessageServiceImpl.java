package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;
import com.AWBD_Istrate_Moraru.demo.entity.ChatMessage;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.mapper.ChatMessageMapper;
import com.AWBD_Istrate_Moraru.demo.mapper.UserMapper;
import com.AWBD_Istrate_Moraru.demo.repository.ChatMessageRepository;
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
@RequiredArgsConstructor()
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserMapper userMapper;

    @Override
    public ChatMessageDto saveMessage(String senderUsername, Long receiverId, String content) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        boolean areFriends = friendshipRepository.existsBySenderAndReceiverAndStatus(sender, receiver, FriendshipStatus.ACCEPTED)
                || friendshipRepository.existsBySenderAndReceiverAndStatus(receiver, sender, FriendshipStatus.ACCEPTED);

        if (!areFriends) {
            throw new AccessDeniedException("You can only message your friends.");
        }

        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());

        ChatMessage saved = chatMessageRepository.save(message);
        return chatMessageMapper.toDto(saved);
    }

    @Override
    public List<ChatMessageDto> getChatBetweenUsers(String username, Long otherUserId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        User other = userRepository.findById(otherUserId).orElseThrow();
        List<ChatMessage> messages = chatMessageRepository
                .findBySenderAndReceiverOrSenderAndReceiver(user, other, other, user);

        return messages.stream().map(chatMessageMapper::toDto).toList();
    }

}
