package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.ReviewDto;
import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import com.AWBD_Istrate_Moraru.demo.service.ReviewService;
import com.AWBD_Istrate_Moraru.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/new")
    public String createReview(@ModelAttribute ReviewDto reviewDto, Principal principal) {
        reviewDto.setCreatedAt(LocalDate.now());

        Optional<UserDto> me = userService.findByUsername(principal.getName());
        if (!me.isEmpty())
            reviewDto.setUser(me.get());

        reviewService.save(reviewDto);

        return "redirect:/games/" + reviewDto.getGame().getId();
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ReviewDto reviewDto = reviewService.findById(id);
        model.addAttribute("reviewDto", reviewDto);

        List<ReviewDto> reviewDtos = reviewService.findAll();
        model.addAttribute("reviewDtos", reviewDtos );

        return "reviewForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        reviewService.deleteById(id);
        return "redirect:/reviews";
    }
}