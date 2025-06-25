package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.PublisherDto;
import com.AWBD_Istrate_Moraru.demo.entity.Publisher;
import com.AWBD_Istrate_Moraru.demo.mapper.PublisherMapper;
import com.AWBD_Istrate_Moraru.demo.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {
    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;

    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }


    @Override
    public PublisherDto save(PublisherDto publisherDto) {
        Publisher savedPublisher = publisherRepository.save(publisherMapper.toPublisher(publisherDto));
        return publisherMapper.toPublisherDto(savedPublisher);
    }

    @Override
    public PublisherDto findById(Long id) {
        Optional<Publisher> publisherOpt = publisherRepository.findById(id);

        if (publisherOpt.isEmpty()) {
            throw new RuntimeException("Publisher not found");
        }

        return publisherMapper.toPublisherDto(publisherOpt.get());
    }

    @Override
    public List<PublisherDto> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();

        return publishers.stream()
                .map(d -> publisherMapper.toPublisherDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }
}
