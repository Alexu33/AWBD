package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartDto save(CartDto cartDto);

    CartDto findById(Long id);

    List<CartDto> findAll();

    void deleteById(Long id);
}
