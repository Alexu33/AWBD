package com.AWBD_Istrate_Moraru.demo.dto;

import com.AWBD_Istrate_Moraru.demo.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDate joinDate;
    private Roles role;
}
