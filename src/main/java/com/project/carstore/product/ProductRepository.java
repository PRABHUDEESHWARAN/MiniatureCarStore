package com.project.carstore.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

<<<<<<< HEAD
    List<Product> findByPrice(Double price);
=======
    List<Product> findByPrice(double price);
>>>>>>> b1158df34438c58e145bd05676dbe8fe3e09ef15

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String description);

}
