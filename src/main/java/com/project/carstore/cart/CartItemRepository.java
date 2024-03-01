package com.project.carstore.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findCartItemByCartIdAndProductId(Integer cartId, Long productId);

    Optional<CartItem> findCartItemByProductId(Long productId);
}