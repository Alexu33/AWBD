package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GenreDto;
import com.AWBD_Istrate_Moraru.demo.entity.Genre;
import com.AWBD_Istrate_Moraru.demo.mapper.GenreMapper;
import com.AWBD_Istrate_Moraru.demo.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GenreServiceImpl implements GenreService {
    GenreRepository genreRepository;
    GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }


    @Override
    public GenreDto save(GenreDto genreDto) {
        Genre savedGenre = genreRepository.save(genreMapper.toEntity(genreDto));
        return genreMapper.toDto(savedGenre);
    }

    @Override
    public GenreDto findById(Long id) {
        Optional<Genre> genreOpt = genreRepository.findById(id);

        if (genreOpt.isEmpty()) {
            throw new RuntimeException("Genre not found");
        }

        return genreMapper.toDto(genreOpt.get());
    }

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream()
                .map(d -> genreMapper.toDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
