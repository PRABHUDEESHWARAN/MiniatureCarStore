package com.project.carstore.product;

import com.project.carstore.exceptions.ProductException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product addProductToDb(ProductDTO product) throws ProductException;
    public Product deleteProductFromDb(Long Id) throws ProductException;
    public Product updateProductInDb(UpdateProductDTO productDetails) throws ProductException;
    Optional<Product> getProductById(Long id) throws ProductException;
    List<Product> getAllProducts() throws ProductException;
    List<Product> getAllProductsByPrice(Double price) throws ProductException;
    List<Product> getAllProductsByPriceRange(Double min,Double max) throws ProductException;
    List<Product> getAllProductsSortedByPrice() throws ProductException;
    List<Product> getAllProductsByName(String name) throws ProductException;
    List<Product> getAllProductsByDescription(String description) throws ProductException;

}
