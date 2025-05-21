package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.DeveloperDto;
import com.AWBD_Istrate_Moraru.demo.entity.Developer;
import com.AWBD_Istrate_Moraru.demo.mapper.DeveloperMapper;
import com.AWBD_Istrate_Moraru.demo.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    DeveloperRepository developerRepository;
    DeveloperMapper developerMapper;

    public DeveloperServiceImpl(DeveloperRepository developerRepository, DeveloperMapper developerMapper) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
    }


    @Override
    public DeveloperDto save(DeveloperDto developerDto) {
        Developer savedDeveloper = developerRepository.save(developerMapper.toDeveloper(developerDto));
        return developerMapper.toDeveloperDto(savedDeveloper);
    }

    @Override
    public DeveloperDto findById(Long id) {
        Optional<Developer> developerOpt = developerRepository.findById(id);

        if (developerOpt.isEmpty()) {
            throw new RuntimeException("Developer not found");
        }

        return developerMapper.toDeveloperDto(developerOpt.get());
    }

    @Override
    public List<DeveloperDto> findAll() {
        List<Developer> developers = developerRepository.findAll();

        return developers.stream()
                         .map(d -> developerMapper.toDeveloperDto(d))
                         .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }
}
