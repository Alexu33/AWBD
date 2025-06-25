package com.AWBD_Istrate_Moraru.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private String content;
    private LocalDateTime sentAt;
}
