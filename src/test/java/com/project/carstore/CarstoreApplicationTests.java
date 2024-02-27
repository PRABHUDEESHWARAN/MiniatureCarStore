package com.project.carstore;

import com.project.carstore.product.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest
class CarstoreApplicationTests {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	//addproduct tests
	@Test
	@DisplayName(value = "adding product")
	public void addProductTest_validProduct() throws ProductException {
		Product product = new Product("pen", 20.0, "string", "image.com", "white", 30);
		product = this.productService.addProductToDb(product);
		Assertions.assertNotNull(product);
	}

	@Test
	@DisplayName(value = "adding null product")
	public void addProductTest_NullProduct() throws ProductException {
		Assertions.assertThrows(ProductException.class, () -> {
			productService.addProductToDb(null);
		});
	}

	//deleteproduct tests
    @Test
    @DisplayName(value = "deleting product with valid id")
    public void deleteProductTest_ValidId() throws ProductException{
        Product product = new Product("pen", 20.0, "string", "image.com", "white", 30);
        Product savedProduct = productRepository.save(product);
        Product deletedProduct = productService.deleteProductFromDb(savedProduct.getId());
        Assertions.assertNull(productRepository.findById(savedProduct.getId()).orElse(null));
        Assertions.assertNotNull(deletedProduct);
    }
    @Test
	@DisplayName(value = "deleting product with invalid id")
	public void deleteProductTest_InvalidId() throws ProductException {
		Assertions.assertThrows(ProductException.class, () -> {
			productService.deleteProductFromDb(null);
		});
	}

	@Test
	@DisplayName(value = "deleting product with non existing id")
	public void deleteProductTest_NonExistingId() throws ProductException {
		Assertions.assertThrows(ProductException.class, () -> {
			productService.deleteProductFromDb(Long.MAX_VALUE);
		});
	}

	//updateproduct test
	@Test
	@DisplayName(value = "updating null product")
	public void updateProductTest_NullProduct() throws ProductException {
		Assertions.assertThrows(ProductException.class, () -> {
			productService.updateProductInDb(null);
		});
	}


	//getallproducts tests
	@Test
	@DisplayName(value = "getting all products test with null products")
	public void getAllProductsTest_NullProducts() throws ProductException {
		productRepository.deleteAll();
		Assertions.assertThrows(ProductException.class, () -> {
			productService.getAllProducts();
		});
	}

	@Test
	@DisplayName(value = "getting all products")
	public void getAllProductsTest() throws ProductException {
		Product product1 = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product product2 = new Product("pencil", 10.0, "string", "image.com", "black", 50);
		productRepository.save(product1);
		productRepository.save(product2);
		List<Product> products = productService.getAllProducts();
		Assertions.assertNotNull(products);
		Assertions.assertFalse(products.isEmpty());

	}

	//getproductbyId test
	@Test
	@DisplayName(value = "getting product by valid id")
	public void getProductByIdTest_ValidId() throws ProductException {
		Product product = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product saved = productRepository.save(product);
		Product retrieved = productService.getProductById(saved.getId());
		Assertions.assertNotNull(retrieved);
		Assertions.assertEquals(saved.getId(), retrieved.getId());
	}


    //getallproductsbyprice test
	@Test
	@DisplayName(value = "getting all products by price with null product")
	public void getAllProductByPriceTest_NullProduct() throws ProductException {
		productRepository.deleteAll();
		Assertions.assertThrows(ProductException.class, () -> {
			productService.getAllProductsByPrice(10.0);
		});
	}

	@Test
	@DisplayName(value = "getting all products by price")
	public void getAllProductByPriceTest() throws ProductException {
		Double price = 20.0;
		Product product1 = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product product2 = new Product("pencil", 10.0, "string", "image.com", "black", 50);
		productRepository.save(product1);
		productRepository.save(product2);
		List<Product> products = productService.getAllProductsByPrice(price);
		Assertions.assertNotNull(products);
		Assertions.assertFalse(products.isEmpty());
	}

	//getallproductsbypricerange test
	@Test
	@DisplayName(value = "getting all products by price range-null products")
	public void getAllProductsByPriceRangeTest_NullProducts() throws ProductException {
		productRepository.deleteAll();
		Assertions.assertThrows(ProductException.class, () -> {
			productService.getAllProductsByPriceRange(10.0, 50.0);
		});
	}

	@Test
	@DisplayName(value = "getting all products by price range")
	public void getAllProductsByPriceRangeTest() throws ProductException {
		Double price1 = 10.0;
		Double price2 = 50.0;
		Product product1 = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product product2 = new Product("pencil", 10.0, "string", "image.com", "black", 50);
		Product product3 = new Product("eraser", 5.0, "string", "image.com", "white", 80);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		List<Product> products = productService.getAllProductsByPriceRange(price1, price2);
		Assertions.assertNotNull(products);
		Assertions.assertFalse(products.isEmpty());
		for (Product product : products) {
			Assertions.assertTrue(product.getPrice() >= price1 && product.getPrice() <= price2);
		}
	}

	//getallproductssortedbyprice test
	@Test
	@DisplayName(value = "getting all products sorted by price-null products")
	public void getAllProductsSortedByPriceTest_NullProducts() throws ProductException {
		productRepository.deleteAll();
		Assertions.assertThrows(ProductException.class, () -> {
			productService.getAllProductsSortedByPrice();
		});
	}
	@Test
	@DisplayName(value = "getting all products sorted by price")
	public void getAllProductsSortedByPriceTest () throws ProductException {
		Product product1 = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product product2 = new Product("pencil", 10.0, "string", "image.com", "black", 50);
		Product product3 = new Product("eraser", 5.0, "string", "image.com", "white", 80);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		List<Product> products = productService.getAllProductsSortedByPrice();
		Assertions.assertNotNull(products);
		Assertions.assertFalse(products.isEmpty());
	}

	//getproductsbtname test
	@Test
	@DisplayName(value = "getting all products by name - null product")
	public void getAllProductByNameTest_NullProduct() throws ProductException{
			productRepository.deleteAll();
			Assertions.assertThrows(ProductException.class, () -> {
		productService.getAllProductsByName("sharpener");
	});
    }
	@Test
	@DisplayName(value = "getting all products by name")
	public void getAllProductsByNameTest () throws ProductException {
		String name="pencil";
		Product product1 = new Product("pen", 20.0, "string", "image.com", "white", 30);
		Product product2 = new Product("pencil", 10.0, "string", "image.com", "black", 50);
		Product product3 = new Product("eraser", 5.0, "string", "image.com", "white", 80);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		List<Product> products = productService.getAllProductsByName(name);
		Assertions.assertNotNull(products);
	}

    //getallproductsbydescription test
    @Test
    @DisplayName(value = "getting all products by description - null product")
    public void getAllProductByDescriptionTest_NullProduct() throws ProductException{
        productRepository.deleteAll();
        Assertions.assertThrows(ProductException.class, () -> {
            productService.getAllProductsByDescription("description");
        });
    }
    @Test
    @DisplayName(value = "getting all products by description")
    public void getAllProductsByDescriptionTest () throws ProductException {
        String description="sting";
        Product product1 = new Product("pen", 20.0, "sting", "image.com", "white", 30);
        Product product2 = new Product("pencil", 10.0, "sing", "image.com", "black", 50);
        Product product3 = new Product("eraser", 5.0, "ring", "image.com", "white", 80);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        List<Product> products = productService.getAllProductsByDescription(description);
        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());
    }

}



