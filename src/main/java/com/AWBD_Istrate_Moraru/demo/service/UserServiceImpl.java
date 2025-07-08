package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.UserCreateDto;
import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.mapper.UserMapper;
import com.AWBD_Istrate_Moraru.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto save(UserDto userDto) {
        User savedUser = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return userMapper.toUserDto(userOpt.get());
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(d -> userMapper.toUserDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toUserDto);
    }

    @Override
    public void updateUserFromProfile(String username, UserCreateDto userCreateDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if(userRepository.findByUsername(userCreateDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());

        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        }

        userRepository.save(user);
    }


}
