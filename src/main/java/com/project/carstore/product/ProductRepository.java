package com.project.carstore.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> findByPrice(double price);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String description);
}
