package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.FriendshipDto;
import com.AWBD_Istrate_Moraru.demo.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface FriendshipMapper {
    FriendshipDto toFriendshipDto(Friendship friendship);

    @Mapping(target = "sender.password", ignore = true)
    @Mapping(target = "receiver.password", ignore = true)
    Friendship fromFriendshipDto(FriendshipDto friendshipDto);
}
