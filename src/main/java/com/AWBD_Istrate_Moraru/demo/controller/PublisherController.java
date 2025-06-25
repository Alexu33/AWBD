package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.PublisherDto;
import com.AWBD_Istrate_Moraru.demo.service.PublisherService;
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
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("")
    public String createOrUpdatePublisher(@ModelAttribute PublisherDto publisherDto) {
        publisherService.save(publisherDto);

        return "redirect:/publishers";
    }

    @RequestMapping("")
    public String publisherList(Model model) {
        List<PublisherDto> publisherDtos = publisherService.findAll();
        log.info("Publisher List: {}", publisherDtos.size());
        model.addAttribute("publisherDtos", publisherDtos);
        return "publisherList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PublisherDto publisherDto = publisherService.findById(id);
        model.addAttribute("publisherDto", publisherDto);

        List<PublisherDto> publisherDtos = publisherService.findAll();
        model.addAttribute("publisherDtos", publisherDtos );

        return "publisherForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        publisherService.deleteById(id);
        return "redirect:/publishers";
    }
}