package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findBySenderId(Long senderId);
    Optional<Purchase> findByReceiverId(Long receiverId);
    List<Purchase> findAllByReceiverId(Long receiverId);
}
