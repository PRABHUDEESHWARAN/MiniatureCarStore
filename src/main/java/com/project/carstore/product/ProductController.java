package com.project.carstore.product;
import com.project.carstore.exceptions.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/new")
    public ResponseEntity<Product> addProductToDb(@RequestBody ProductDTO product) throws ProductException
    {
        Product addedProduct;
        try {
            addedProduct= productService.addProductToDb(product);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(addedProduct, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Product> deleteProductFromDb(@PathVariable("Id") String id) throws ProductException {
        Product deletedProduct;
        try {
            deletedProduct=this.productService.deleteProductFromDb(id);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(deletedProduct,HttpStatus.ACCEPTED);
    }

    @PatchMapping()
    public ResponseEntity<Product> updateProductInDb(@RequestBody UpdateProductDTO productDetails) throws ProductException {
        Product updatedProduct;
        try {
            updatedProduct=this.productService.updateProductInDb(productDetails);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(updatedProduct,HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductException{
        List<Product> products;
        try {
            products=this.productService.getAllProducts();
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long productId) throws ProductException {
       return this.productService.getProductById(productId);
    }
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getAllProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) throws ProductException {
        List<Product> products;
        try {
            products=this.productService.getAllProductsByPriceRange(min,max);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/sorted-by-price")
    public ResponseEntity<List<Product>> getAllProductsSortedByPrice() throws ProductException{
        List<Product> products;
        try {
            products=this.productService.getAllProductsSortedByPrice();
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam String name) throws ProductException{
        List<Product> products;
        try {
            products=this.productService.getAllProductsByName(name);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<List<Product>> getAllProductsByDescription(@RequestParam String description) throws ProductException{
        List<Product> products;
        try {
            products=this.productService.getAllProductsByDescription(description);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/price/{price}")
    public ResponseEntity<List<Product>> getAllProductsByPrice(@PathVariable("price") Double price) throws ProductException{
        List<Product> productsList;
        productsList=this.productService.getAllProductsByPrice(price);
        return new ResponseEntity<>(productsList,HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@RequestParam("category") String category) {
        return new ResponseEntity<>(this.productService.getAllProductsByCategory(category), HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> searchProducts(@RequestParam(""))

}
