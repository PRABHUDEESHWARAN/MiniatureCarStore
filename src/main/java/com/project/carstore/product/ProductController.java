package com.project.carstore.product;

import com.project.carstore.exceptions.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping("/")
    public ResponseEntity<Product> addProductToDb(@RequestBody ProductDTO product)
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
    public ResponseEntity<Product> deleteProductFromDb(@PathVariable("Id") Long Id)
    {
        Product deletedProduct=null;
        try {
            deletedProduct=this.productService.deleteProductFromDb(Id);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Product>(deletedProduct,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/updateProduct")
    public ResponseEntity<Product> updateProductInDb(@RequestBody ProductDTO product)
    {
        Product updatedProduct=null;
        try {
            updatedProduct=this.productService.updateProductInDb(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Product>(updatedProduct,HttpStatus.ACCEPTED);
    }


}
