package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.UserCreateDto;
import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    void deleteById(Long id);

    Optional<UserDto> findByUsername(String username);

    void updateUserFromProfile(String username, UserCreateDto userCreateDto);
}
