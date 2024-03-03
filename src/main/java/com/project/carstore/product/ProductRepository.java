package com.project.carstore.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {



    List<Product> findByPrice(double price);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findByNameContainingIgnoreCase(String name);
    Product findProductByName(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String description);

    Boolean existsByName(String name);
    Boolean existsByNameIgnoreCase(String name);

    List<Product> findAllByCategory(String category);
}
