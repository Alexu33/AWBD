package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
