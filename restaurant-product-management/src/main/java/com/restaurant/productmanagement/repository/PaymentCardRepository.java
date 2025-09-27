package com.restaurant.productmanagement.repository;

import com.restaurant.productmanagement.model.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    Optional<PaymentCard> findByIdAndUserEmail(Long id, String userEmail);
    boolean existsByUserEmail(String userEmail);
}
