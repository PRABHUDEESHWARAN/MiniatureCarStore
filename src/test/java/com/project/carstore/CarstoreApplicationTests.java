package com.project.carstore;

import com.project.carstore.product.Product;
import com.project.carstore.product.ProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.carstore.product.ProductService;

@SpringBootTest
class CarstoreApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	@DisplayName(value = "adding product")
	public void addProductTest()throws ProductException {
		Product product = new Product("pen",20.0,"string","imnk","white",30);
		product=this.productService.addProductToDb(product);
		Assertions.assertNotNull(product);
	}
}
