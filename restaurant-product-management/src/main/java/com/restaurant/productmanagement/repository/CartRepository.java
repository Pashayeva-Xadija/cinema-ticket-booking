package com.restaurant.productmanagement.repository;

import com.restaurant.productmanagement.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    void deleteByUserEmail(String email);
}
