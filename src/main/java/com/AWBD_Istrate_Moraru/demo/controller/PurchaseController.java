package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import com.AWBD_Istrate_Moraru.demo.service.PurchaseService;
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
@RequestMapping("/purchases")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("")
    public String createOrUpdatePurchase(@ModelAttribute PurchaseDto purchaseDto) {
        purchaseService.save(purchaseDto);

        return "redirect:/purchases";
    }

    @RequestMapping("")
    public String purchaseList(Model model) {
        List<PurchaseDto> purchaseDtos = purchaseService.findAll();
        log.info("Purchase List: {}", purchaseDtos.size());
        model.addAttribute("purchaseDtos", purchaseDtos);
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
}