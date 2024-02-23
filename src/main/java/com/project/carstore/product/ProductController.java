package com.project.carstore.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    Product product=null;
    @PostMapping("/addProduct")
<<<<<<< HEAD
    public ResponseEntity<Product> addProductToDb(@RequestBody ProductDTO product)throws ProductException
=======
    public ResponseEntity<Product> addProductToDb(@RequestBody ProductDTO product) throws ProductException
>>>>>>> b1158df34438c58e145bd05676dbe8fe3e09ef15
    {
        Product addedProduct=null;
        try {
            addedProduct= productService.addProductToDb(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Product>(addedProduct, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteProduct/{Id}")
<<<<<<< HEAD
    public ResponseEntity<Product> deleteProductFromDb(@PathVariable("Id") Long id) throws ProductException
=======
    public ResponseEntity<Product> deleteProductFromDb(@PathVariable("Id") Long Id) throws ProductException
>>>>>>> b1158df34438c58e145bd05676dbe8fe3e09ef15
    {
        Product deletedProduct=null;
        try {
            deletedProduct=this.productService.deleteProductFromDb(id);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Product>(deletedProduct,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/updateProduct")
    public ResponseEntity<Product> updateProductInDb(@RequestBody ProductDTO product) throws ProductException
    {
        Product updatedProduct=null;
        try {
            updatedProduct=this.productService.updateProductInDb(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Product>(updatedProduct,HttpStatus.ACCEPTED);
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductException
    {
        List<Product> products=null;
        try {
            products=this.productService.getAllProducts();
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long productId) throws ProductException
    {
        Product product=null;
        try {
            product=this.productService.getProductById(productId);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
       if(product!=null){
           return new ResponseEntity<>(product, HttpStatus.OK).getBody();
       }else{
           throw new ProductException("Product not found"+productId);
       }
    }
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getAllProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) throws ProductException
    {
        List<Product> products=null;
        try {
            products=this.productService.getAllProductsByPriceRange(min,max);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/sorted-by-price")
    public ResponseEntity<List<Product>> getAllProductsSortedByPrice() throws ProductException
    {
        List<Product> products=null;
        try {
            products=this.productService.getAllProductsSortedByPrice();
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get-product-by-name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam String name) throws ProductException
    {
        List<Product> products=null;
        try {
            products=this.productService.getAllProductsByName(name);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/get-product-by-description")
    public ResponseEntity<List<Product>> getAllProductsByDescription(@RequestParam String description) throws ProductException
    {
        List<Product> products=null;
        try {
            products=this.productService.getAllProductsByDescription(description);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
