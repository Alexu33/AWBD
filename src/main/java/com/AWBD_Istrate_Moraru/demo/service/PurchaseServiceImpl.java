package com.AWBD_Istrate_Moraru.demo.service;

import com.AWBD_Istrate_Moraru.demo.dto.GameDto;
import com.AWBD_Istrate_Moraru.demo.dto.PurchaseDto;
import com.AWBD_Istrate_Moraru.demo.entity.Game;
import com.AWBD_Istrate_Moraru.demo.entity.Purchase;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.mapper.GameMapper;
import com.AWBD_Istrate_Moraru.demo.mapper.PurchaseMapper;
import com.AWBD_Istrate_Moraru.demo.repository.GameRepository;
import com.AWBD_Istrate_Moraru.demo.repository.PurchaseRepository;
import com.AWBD_Istrate_Moraru.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    PurchaseRepository purchaseRepository;
    PurchaseMapper purchaseMapper;
    GameMapper gameMapper;
    UserRepository userRepository;
    GameRepository gameRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper,GameMapper gameMapper, UserRepository userRepository, GameRepository gameRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.gameMapper = gameMapper;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public PurchaseDto save(PurchaseDto purchaseDto) {
        Long receiverId = purchaseDto.getReceiver().getId();
        Long gameId = purchaseDto.getGame().getId();

        if (purchaseRepository.existsByReceiverIdAndGameId(receiverId, gameId)) {
            throw new RuntimeException("User already owns this game.");
        }

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

    @Override
    public void simulatePurchase(String senderUsername, Long receiverId, Long gameId) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Purchase purchase = new Purchase();
        purchase.setSender(sender);
        purchase.setReceiver(receiver);
        purchase.setGame(game);
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setPricePaid(game.getPrice());
        purchase.setComment("Simulated purchase");

        purchaseRepository.save(purchase);
    }

    @Override
    public boolean hasUserPurchasedGame(Long userId, Long gameId) {
        return purchaseRepository.existsByReceiverIdAndGameId(userId, gameId);
    }

    @Override
    public List<PurchaseDto> findAllPurchasesBySenderId(Long userId) {
        List<Purchase> purchases = purchaseRepository.findAllPurchasesBySenderId(userId);

        return purchases.stream()
                .map(purchaseMapper::toPurchaseDto)
                .collect(Collectors.toList());
    }
}
