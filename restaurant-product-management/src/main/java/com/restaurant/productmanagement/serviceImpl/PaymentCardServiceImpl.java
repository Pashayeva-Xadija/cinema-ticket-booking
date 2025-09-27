package com.restaurant.productmanagement.serviceImpl;

import com.restaurant.productmanagement.dto.AddCardRequest;
import com.restaurant.productmanagement.dto.PaymentCardDto;
import com.restaurant.productmanagement.mapper.PaymentCardMapper;
import com.restaurant.productmanagement.model.PaymentCard;
import com.restaurant.productmanagement.repository.PaymentCardRepository;
import com.restaurant.productmanagement.service.PaymentCardService;
import com.restaurant.productmanagement.service.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository repo;
    private final PaymentCardMapper mapper;
    private final PaymentGateway gateway;

    @Override
    public List<PaymentCardDto> myCards(String userEmail) {
        return repo.findByUserEmailOrderByCreatedAtDesc(userEmail).stream()
                .map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public PaymentCardDto addCard(String userEmail, AddCardRequest req) {

        if (req.getCardNumber() == null || req.getCardNumber().length() < 12)
            throw new RuntimeException("Kart nömrəsi yanlışdır");

        String cardNo = req.getCardNumber().replaceAll("\\s+", "");
        String last4 = cardNo.substring(cardNo.length()-4);
        String brand = cardNo.startsWith("4") ? "VISA" :
                (cardNo.startsWith("5") ? "MASTERCARD" : "CARD");
        String token = gateway.tokenizeCard(cardNo, req.getExpMonth(), req.getExpYear(), req.getCvc());

        PaymentCard card = PaymentCard.builder()
                .userEmail(userEmail)
                .brand(brand)
                .last4(last4)
                .expMonth(req.getExpMonth())
                .expYear(req.getExpYear())
                .providerToken(token)
                .defaultCard(!repo.existsByUserEmail(userEmail))
                .build();

        return mapper.toDto(repo.save(card));
    }

    @Override
    @Transactional
    public void remove(String userEmail, Long id) {
        PaymentCard c = repo.findByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new RuntimeException("Kart tapılmadı"));
        repo.delete(c);
    }

    @Override
    @Transactional
    public void setDefault(String userEmail, Long id) {
        var cards = repo.findByUserEmailOrderByCreatedAtDesc(userEmail);
        boolean found = false;
        for (PaymentCard c : cards) {
            boolean isDefault = c.getId().equals(id);
            if (isDefault) found = true;
            c.setDefaultCard(isDefault);
        }
        if (!found) throw new RuntimeException("Kart tapılmadı");
        repo.saveAll(cards);
    }
}
