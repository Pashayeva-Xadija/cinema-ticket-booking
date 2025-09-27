package com.restaurant.productmanagement.serviceImpl;

import com.restaurant.productmanagement.dto.CheckoutRequest;
import com.restaurant.productmanagement.dto.OrderDto;
import com.restaurant.productmanagement.enums.OrderStatus;
import com.restaurant.productmanagement.enums.PaymentMethod;
import com.restaurant.productmanagement.enums.PaymentStatus;
import com.restaurant.productmanagement.mapper.OrderMapper;
import com.restaurant.productmanagement.model.*;
import com.restaurant.productmanagement.repository.CartRepository;
import com.restaurant.productmanagement.repository.OrderRepository;
import com.restaurant.productmanagement.service.OrderService;
import com.restaurant.productmanagement.service.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentGateway paymentGateway;

    @Override
    public List<OrderDto> myOrders(String userEmail) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(userEmail)
                .stream().map(orderMapper::toDto).toList();
    }

    @Override
    public OrderDto getById(Long id, String userEmail) {
        CustomerOrder o = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı"));
        if (!o.getUserEmail().equals(userEmail)) {
            throw new RuntimeException("Giriş icazəsi yoxdur");
        }
        return orderMapper.toDto(o);
    }

    @Override
    @Transactional
    public OrderDto checkout(String userEmail, CheckoutRequest request) {
        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Səbət boşdur"));


        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.signum() <= 0) throw new RuntimeException("Səbət boşdur");


        String addr = request.getDeliveryAddress();
        boolean isDelivery = addr != null && !addr.isBlank();
        BigDecimal fee = isDelivery
                ? (request.getDeliveryFee() != null ? request.getDeliveryFee() : BigDecimal.ZERO)
                : BigDecimal.ZERO;

        BigDecimal grand = total.add(fee);


        CustomerOrder order = CustomerOrder.builder()
                .userEmail(userEmail)
                .status(OrderStatus.NEW)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .totalAmount(grand)
                .deliveryAddress(isDelivery ? addr : null)
                .deliveryFee(fee)
                .createdAt(Instant.now())
                .build();


        order.setItems(cart.getItems().stream().map(ci -> OrderItem.builder()
                .order(order)
                .productId(ci.getProduct().getId())
                .productName(ci.getProduct().getName())
                .unitPrice(ci.getUnitPrice())
                .quantity(ci.getQuantity())
                .lineTotal(ci.getLineTotal())
                .build()).toList());


        if (request.getPaymentMethod() == PaymentMethod.ONLINE) {
            String ref = paymentGateway.createPayment(grand, "Order payment"); // <-- grand göndər
            order.setPaymentProviderRef(ref);
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }


        CustomerOrder saved = orderRepository.save(order);
        cartRepository.delete(cart);

        return orderMapper.toDto(saved);
    }


    @Override
    @Transactional
    public OrderDto markOfflinePaid(Long id) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı"));
        if (order.getPaymentMethod() != PaymentMethod.OFFLINE) {
            throw new RuntimeException("Bu sifariş offline ödəniş deyil");
        }
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setStatus(OrderStatus.CONFIRMED);
        return orderMapper.toDto(orderRepository.save(order));
    }
}
