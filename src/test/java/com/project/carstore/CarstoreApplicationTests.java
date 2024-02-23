package com.project.carstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.carstore.product.ProductService;

@SpringBootTest
class CarstoreApplicationTests {

	@Autowired
	private ProductService productService;

}
