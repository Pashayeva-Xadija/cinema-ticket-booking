package com.restaurant.productmanagement.serviceImpl;

import com.restaurant.productmanagement.dto.AddToCartRequest;
import com.restaurant.productmanagement.dto.CartDto;
import com.restaurant.productmanagement.mapper.CartMapper;
import com.restaurant.productmanagement.model.Cart;
import com.restaurant.productmanagement.model.CartItem;
import com.restaurant.productmanagement.model.Product;
import com.restaurant.productmanagement.repository.CartRepository;
import com.restaurant.productmanagement.repository.ProductRepository;
import com.restaurant.productmanagement.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;


    private Cart getOrCreate(String email) {
        return cartRepository.findByUserEmail(email)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userEmail(email)
                        .build()));
    }

    @Override
    public CartDto getMyCart(String userEmail) {
        return cartMapper.toDto(getOrCreate(userEmail));
    }

    @Override
    @Transactional
    public CartDto addItem(String userEmail, AddToCartRequest req) {
        Cart cart = getOrCreate(userEmail);

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Məhsul tapılmadı"));


        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        int addQty = Math.max(1, req.getQuantity() == null ? 1 : req.getQuantity());

        if (item == null) {
            item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .unitPrice(product.getPrice())
                    .quantity(addQty)
                    .build();
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + addQty);
        }

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDto updateQuantity(String userEmail, Long productId, int quantity) {
        if (quantity <= 0) {
            return removeItem(userEmail, productId);
        }

        Cart cart = getOrCreate(userEmail);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Məhsul səbətdə yoxdur"));

        item.setQuantity(quantity);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDto removeItem(String userEmail, Long productId) {
        Cart cart = getOrCreate(userEmail);
        boolean removed = cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        if (!removed) throw new RuntimeException("Məhsul səbətdə yoxdur");
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void clear(String userEmail) {
        // Əgər CartRepository-də deleteByUserEmail varsa, onu çağır:
        try {
            cartRepository.deleteByUserEmail(userEmail);
        } catch (Exception ignored) {
            cartRepository.findByUserEmail(userEmail).ifPresent(cartRepository::delete);
        }
    }
}
