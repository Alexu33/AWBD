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

@Service
@RequiredArgsConstructor()
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserMapper userMapper;

    @Override
    public ChatMessageDto saveMessage(ChatMessageDto dto, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiver().getId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        boolean areFriends = friendshipRepository.existsBySenderAndReceiverAndStatus(sender, receiver, FriendshipStatus.ACCEPTED)
                || friendshipRepository.existsBySenderAndReceiverAndStatus(receiver, sender, FriendshipStatus.ACCEPTED);

        if (!areFriends) {
            throw new AccessDeniedException("You can only message your friends.");
        }

        ChatMessage message = chatMessageMapper.toEntity(dto);

        ChatMessage saved = chatMessageRepository.save(message);
        return chatMessageMapper.toDto(saved);
    }

}
