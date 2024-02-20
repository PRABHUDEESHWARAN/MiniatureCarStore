package com.project.carstore.product;

import com.project.carstore.exceptions.ProductException;

public interface ProductService {

    public Product addProductToDb(ProductDTO product) throws ProductException;
    public Product deleteProductFromDb(Long Id) throws ProductException;
    public Product updateProductInDb(ProductDTO product) throws ProductException;



}
