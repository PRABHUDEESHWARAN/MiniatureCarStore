package com.project.carstore;

import com.project.carstore.exceptions.ProductException;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.carstore.product.ProductService;

import java.time.LocalDate;

@SpringBootTest
class CarstoreApplicationTests {

	@Autowired
	private ProductService productService;



}
