package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.ChatMessageDto;
import com.AWBD_Istrate_Moraru.demo.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ChatMessageMapper {
    ChatMessageDto toDto(ChatMessage chatMessage);
    ChatMessage toEntity(ChatMessageDto chatMessageDto);
}
