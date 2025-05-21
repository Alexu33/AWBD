package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    void deleteById(Long id);
}
