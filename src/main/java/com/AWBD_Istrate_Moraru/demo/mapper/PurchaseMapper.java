package com.AWBD_Istrate_Moraru.demo.mapper;

import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import com.AWBD_Istrate_Moraru.demo.entity.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseDto toPurchaseDto(Purchase purchase);
    Purchase toPurchase(PurchaseDto purchaseDto);
}
