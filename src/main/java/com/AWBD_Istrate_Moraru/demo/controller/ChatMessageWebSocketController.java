package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;
import com.AWBD_Istrate_Moraru.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatMessageWebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatService;

//    @MessageMapping("/chat.send")
//    public void sendMessage(ChatMessageDto messageDto, Principal principal) {
//        chatService.saveMessage(messageDto, principal.getName());
//        messagingTemplate.convertAndSendToUser(
//                messageDto.getReceiver().getId().toString(),
//                "/topic/messages",
//                messageDto
//        );
//    }


//    @MessageMapping("/chat.send")
//    public void sendMessage(ChatMessageDto messageDto) {
//        chatService.saveMessage(messageDto, messageDto.getSender().getUsername());
//        messagingTemplate.convertAndSendToUser(
//                messageDto.getReceiver().getId().toString(),
//                "/topic/messages",
//                messageDto
//        );
//    }

}
