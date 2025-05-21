package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import com.AWBD_Istrate_Moraru.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String createOrUpdateUser(@ModelAttribute UserDto userDto) {
        userService.save(userDto);

        return "redirect:/users";
    }

    @RequestMapping("")
    public String userList(Model model) {
        List<UserDto> userDtos = userService.findAll();
        log.info("User List: {}", userDtos.size());
        model.addAttribute("userDtos", userDtos);
        return "userList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findById(id);
        model.addAttribute("userDto", userDto);

        List<UserDto> userDtos = userService.findAll();
        model.addAttribute("userDtos", userDtos );

        return "userForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }
}