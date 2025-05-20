package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.CartDto;
import com.AWBD_Istrate_Moraru.demo.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toCartDto(Cart cart);
    Cart toCart(CartDto cartDto);
}
