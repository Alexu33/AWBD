package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PurchaseService {
    PurchaseDto save(PurchaseDto purchaseDto);

    PurchaseDto findById(Long id);

    List<PurchaseDto> findAll();

    void deleteById(Long id);
}
