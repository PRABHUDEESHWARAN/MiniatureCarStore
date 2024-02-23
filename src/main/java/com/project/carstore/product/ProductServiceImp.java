package com.project.carstore.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProductToDb(Product product) throws ProductException {
        //handle exception here
        if(product==null)
        {
            throw new ProductException("Product cannot be null");
        }
        Product ProductToBeAdded=new Product( product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getColour(), product.getQuantity());
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

    @Override
    public Product updateProductInDb(ProductDTO productDto) throws ProductException {
        //handle exceptions
        if(productDto==null)
        {
            throw new ProductException("Product cannot be null");
        }
        // get product from databse using productID
        Optional<Product> findProduct=this.productRepository.findById(productDto.getId());
        findProduct.get().setName(productDto.getName());
        findProduct.get().setPrice(productDto.getPrice());
        findProduct.get().setDescription(productDto.getDescription());
        findProduct.get().setColour(productDto.getColour());
        findProduct.get().setImageUrl(productDto.getImageUrl());
        findProduct.get().setQuantity(productDto.getQuantity());
        //save the product object to database
        return this.productRepository.save(findProduct.get());
    }

    @Override
    public List<Product> getAllProducts() throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("No Product Found");
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        //handle exceptions
        Optional<Product> foundProduct=this.productRepository.findById(id);
        if(foundProduct==null)
            throw new ProductException("Product not found:"+id);

        return foundProduct.get();
    }

    @Override
    public List<Product> getAllProductsByPrice(Double price) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("Product not found");
        return productRepository.findByPrice(price);
    }

    @Override
    public List<Product> getAllProductsByPriceRange(Double min, Double max) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("No product found");
        List<Product> allProducts=productRepository.findAll();
        List<Product> productsPriceRange;
        productsPriceRange = allProducts.stream().filter(product->product.getPrice() >=min && product.getPrice() <=max).collect(Collectors.toList());
        return productsPriceRange;
    }

    @Override
    public List<Product> getAllProductsSortedByPrice( ) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("No product found");
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> getAllProductsByName(String name) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("No product found");
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> getAllProductsByDescription(String description) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException("No product found");
        return productRepository.findByDescriptionContainingIgnoreCase(description);
    }
}
