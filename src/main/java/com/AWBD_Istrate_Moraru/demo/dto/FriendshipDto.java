package com.AWBD_Istrate_Moraru.demo.dto;

import com.AWBD_Istrate_Moraru.demo.utils.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FriendshipDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private FriendshipStatus status;
    private LocalDateTime requestedAt;
}
