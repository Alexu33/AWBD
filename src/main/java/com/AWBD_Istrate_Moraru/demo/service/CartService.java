package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.CartDto;

import java.util.List;

public interface CartService {
    CartDto save(CartDto cartDto);

    CartDto findById(Long id);

    CartDto findByUserId(Long userId);

    List<CartDto> findAll();

    void deleteById(Long id);
}
