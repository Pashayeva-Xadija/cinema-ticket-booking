package com.restaurant.productmanagement.repository;

import com.restaurant.productmanagement.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUserEmailOrderByCreatedAtDesc(String email);
}
