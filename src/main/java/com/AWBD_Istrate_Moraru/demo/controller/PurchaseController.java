package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import com.AWBD_Istrate_Moraru.demo.dto.UserDto;
import com.AWBD_Istrate_Moraru.demo.service.GameService;
import com.AWBD_Istrate_Moraru.demo.service.PurchaseService;
import com.AWBD_Istrate_Moraru.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private PurchaseService purchaseService;
    private UserService userService;
    private GameService gameService;

    public PurchaseController(PurchaseService purchaseService, UserService userService, GameService gameService) {
        this.purchaseService = purchaseService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping("")
    public String createOrUpdatePurchase(@ModelAttribute PurchaseDto purchaseDto) {
        purchaseService.save(purchaseDto);

        return "redirect:/purchases";
    }

    @RequestMapping("")
    public String purchaseList(Model model, Principal principal) {
        if (principal != null) {
            Long userId = userService.findByUsername(principal.getName()).get().getId();
            List<PurchaseDto> purchaseDtos = purchaseService.findAllPurchasesBySenderId(userId);
            model.addAttribute("purchaseDtos", purchaseDtos);
            log.info("Purchase List: {}", purchaseDtos.size());
        }
        return "purchaseList";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PurchaseDto purchaseDto = purchaseService.findById(id);
        model.addAttribute("purchaseDto", purchaseDto);

        List<PurchaseDto> purchaseDtos = purchaseService.findAll();
        model.addAttribute("purchaseDtos", purchaseDtos );

        return "purchaseForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        purchaseService.deleteById(id);
        return "redirect:/purchases";
    }

    @PostMapping("/buy")
    public String buyGame(@RequestParam Long gameId,
                          @RequestParam Long receiverId,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {

        UserDto sender = userService.findByUsername(principal.getName()).get();
        GameDto game = gameService.findById(gameId);
        UserDto receiver = userService.findById(receiverId);

        if (purchaseService.hasUserPurchasedGame(receiverId, gameId)) {
            redirectAttributes.addFlashAttribute("error", "This user already owns the game.");
            return "redirect:/games";
        }

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setSender(sender);
        purchaseDto.setReceiver(receiver);
        purchaseDto.setGame(game);
        purchaseDto.setPurchaseDate(LocalDate.now());
        purchaseDto.setPricePaid(game.getPrice());
        purchaseDto.setComment("Bought via Steme");

        purchaseService.save(purchaseDto);

        redirectAttributes.addFlashAttribute("success", "Purchase successful!");
        return "redirect:/games";
    }
}