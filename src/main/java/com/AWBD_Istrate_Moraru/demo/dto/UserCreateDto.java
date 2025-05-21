package com.AWBD_Istrate_Moraru.demo.dto;

import com.AWBD_Istrate_Moraru.demo.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    private String username;
    private String email;
    private String password;
    private Roles role;
}
