package com.project.carstore.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    public Optional<CartItem> findCartItemByProductId(Long productId);

}