package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.CartDto;
import com.AWBD_Istrate_Moraru.demo.entity.Cart;
import com.AWBD_Istrate_Moraru.demo.mapper.CartMapper;
import com.AWBD_Istrate_Moraru.demo.repository.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }


    @Override
    public CartDto save(CartDto cartDto) {
        Cart savedCart = cartRepository.save(cartMapper.toCart(cartDto));
        return cartMapper.toCartDto(savedCart);
    }

    @Override
    public CartDto findById(Long id) {
        Optional<Cart> cartOpt = cartRepository.findById(id);

        if (cartOpt.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        return cartMapper.toCartDto(cartOpt.get());
    }

    @Override
    public List<CartDto> findAll() {
        List<Cart> carts = cartRepository.findAll();

        return carts.stream()
                    .map(d -> cartMapper.toCartDto(d))
                    .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
