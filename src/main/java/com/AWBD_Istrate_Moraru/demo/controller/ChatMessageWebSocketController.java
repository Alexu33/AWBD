package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;
import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageInputDto;
import com.AWBD_Istrate_Moraru.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatMessageWebSocketController {

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/chat/history/{friendId}")
    @ResponseBody
    public List<ChatMessageDto> getChatHistory(@PathVariable Long friendId, Principal principal) {
        return chatMessageService.getChatBetweenUsers(principal.getName(), friendId);
    }

    @MessageMapping("/chat")
    public void send(ChatMessageInputDto message, Principal principal) {
        ChatMessageDto savedMessage = chatMessageService.saveMessage(
                principal.getName(), message.getReceiverId(), message.getContent()
        );

        // Send to sender
        messagingTemplate.convertAndSendToUser(
                principal.getName(), "/queue/messages", savedMessage);

        // Send to receiver
        messagingTemplate.convertAndSendToUser(
                savedMessage.getReceiver().getUsername(), "/queue/messages", savedMessage);

    }
}

