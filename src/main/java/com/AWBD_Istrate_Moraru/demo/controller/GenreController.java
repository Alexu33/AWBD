package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.service.GenreService;
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
@RequestMapping("/genres")
public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("")
    public String createOrUpdateGenre(@ModelAttribute GenreDto genreDto) {
        genreService.save(genreDto);

        return "redirect:/genres";
    }

    @RequestMapping("")
    public String genreList(Model model) {
        List<GenreDto> genreDtos = genreService.findAll();
        log.info("Genre List: {}", genreDtos.size());
        model.addAttribute("genreDtos", genreDtos);
        return "genreList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        GenreDto genreDto = genreService.findById(id);
        model.addAttribute("genreDto", genreDto);

        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genreDtos", genreDtos );

        return "genreForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        genreService.deleteById(id);
        return "redirect:/genres";
    }
}