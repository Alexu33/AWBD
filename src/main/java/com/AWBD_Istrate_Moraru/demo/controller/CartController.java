package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.CartDto;
import com.AWBD_Istrate_Moraru.demo.service.CartService;
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
@RequestMapping("/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("")
    public String createOrUpdateCart(@ModelAttribute CartDto cartDto) {
        cartService.save(cartDto);

        return "redirect:/carts";
    }

    @RequestMapping("")
    public String cartList(Model model) {
        List<CartDto> cartDtos = cartService.findAll();
        log.info("Cart List: {}", cartDtos.size());
        model.addAttribute("cartDtos", cartDtos);
        return "cartList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        CartDto cartDto = cartService.findById(id);
        model.addAttribute("cartDto", cartDto);

        List<CartDto> cartDtos = cartService.findAll();
        model.addAttribute("cartDtos", cartDtos );

        return "cartForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        cartService.deleteById(id);
        return "redirect:/carts";
    }
}
