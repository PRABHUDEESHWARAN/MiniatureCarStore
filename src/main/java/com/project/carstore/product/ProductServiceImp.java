package com.project.carstore.product;

import com.project.carstore.exceptions.ProductException;
import com.project.carstore.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProductToDb(ProductDTO product) throws ProductException {
        //handle exception here
        if(product==null)
        {
            throw new ProductException("Product cannot be null");
        }
        Product ProductToBeAdded=new Product( product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getQuantity());
        return this.productRepository.save(ProductToBeAdded);
    }

    @Override
    public Product deleteProductFromDb(Long Id) throws ProductException {
        //handle exception
        if(Id==null)
        {
            throw new ProductException("Invalid product id");
        }
        // get product from db, if available delete it
        Optional<Product> findProduct=productRepository.findById(Id);
        if(findProduct.isEmpty())
        {
            throw new ProductException("No such product exist with Id:"+ Id);
        }
        this.productRepository.delete(findProduct.get());
        return findProduct.get();
    }

    public Product updateProductInDb(ProductDTO product) throws ProductException {
        //handle exceptions
        if(product==null)
        {
            throw new ProductException("Product cannot be null");
        }
        Product ProductToBeUpdated=new Product( product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getQuantity());
        if(this.productRepository.existsById(ProductToBeUpdated.getId()))
        {
            return this.productRepository.save(ProductToBeUpdated);
        }else{
            throw new ProductException("No Product  exist with Id:"+ProductToBeUpdated.getId());
        }
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return this.productRepository.findById(productId);
    }
}
