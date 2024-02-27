package com.project.carstore.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CartRepository extends JpaRepository<Cart,Integer> {

    Optional<Cart> findByCustomerId(Integer customerId);
}