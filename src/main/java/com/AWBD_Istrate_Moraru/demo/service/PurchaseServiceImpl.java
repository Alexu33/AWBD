package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import com.AWBD_Istrate_Moraru.demo.entity.Purchase;
import com.AWBD_Istrate_Moraru.demo.mapper.PurchaseMapper;
import com.AWBD_Istrate_Moraru.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    PurchaseRepository purchaseRepository;
    PurchaseMapper purchaseMapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
    }


    @Override
    public PurchaseDto save(PurchaseDto purchaseDto) {
        Purchase savedPurchase = purchaseRepository.save(purchaseMapper.toPurchase(purchaseDto));
        return purchaseMapper.toPurchaseDto(savedPurchase);
    }

    @Override
    public PurchaseDto findById(Long id) {
        Optional<Purchase> purchaseOpt = purchaseRepository.findById(id);

        if (purchaseOpt.isEmpty()) {
            throw new RuntimeException("Purchase not found");
        }

        return purchaseMapper.toPurchaseDto(purchaseOpt.get());
    }

    @Override
    public List<PurchaseDto> findAllByUserId(Long userId) {
        List<Purchase> purchases = purchaseRepository.findAllByReceiverId(userId);

        return purchases.stream()
                .map(d -> purchaseMapper.toPurchaseDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findAll() {
        List<Purchase> purchases = purchaseRepository.findAll();

        return purchases.stream()
                .map(d -> purchaseMapper.toPurchaseDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }
}
