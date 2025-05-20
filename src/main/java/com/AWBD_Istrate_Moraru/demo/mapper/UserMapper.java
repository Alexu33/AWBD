package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
