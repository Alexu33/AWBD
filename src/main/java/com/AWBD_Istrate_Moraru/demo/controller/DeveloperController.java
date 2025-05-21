package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.DeveloperDto;
import com.AWBD_Istrate_Moraru.demo.service.DeveloperService;
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
@RequestMapping("/developers")
public class DeveloperController {
    private DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("")
    public String createOrUpdateDeveloper(@ModelAttribute DeveloperDto developerDto) {
        developerService.save(developerDto);

        return "redirect:/developers";
    }

    @RequestMapping("")
    public String developerList(Model model) {
        List<DeveloperDto> developerDtos = developerService.findAll();
        log.info("Developer List: {}", developerDtos.size());
        model.addAttribute("developerDtos", developerDtos);
        return "developerList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        DeveloperDto developerDto = developerService.findById(id);
        model.addAttribute("developerDto", developerDto);

        List<DeveloperDto> developerDtos = developerService.findAll();
        model.addAttribute("developerDtos", developerDtos );

        return "developerForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        developerService.deleteById(id);
        return "redirect:/products";
    }
}
