package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;

public interface ChatMessageService {
    ChatMessageDto saveMessage(ChatMessageDto dto, String senderUsername);
}
