package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    ChatMessageDto saveMessage(String senderUsername, Long receiverId, String content);
    List<ChatMessageDto> getChatBetweenUsers(String username, Long otherUserId);

    Optional<ChatMessageDto> getLastMessageBetween(String username, Long friendId);
}
