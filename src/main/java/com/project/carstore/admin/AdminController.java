package com.project.carstore.admin;

import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.exceptions.ProductException;
import com.project.carstore.order.Order;
import com.project.carstore.order.OrderException;
import com.project.carstore.order.OrderService;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductDTO;
import com.project.carstore.product.ProductService;
import com.project.carstore.product.UpdateProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CustomerService customerService;

    public AdminController(ProductService productService, OrderService orderService, CustomerService customerService) {
        this.productService = productService;
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws CustomerException {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductException {
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProductToDb(@RequestBody ProductDTO     product) throws ProductException
    {
        Product addedProduct;
        try {
            addedProduct= productService.addProductToDb(product);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(addedProduct, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/products/{Id}")
    public ResponseEntity<Product> deleteProductFromDb(@PathVariable("Id") String id) throws ProductException {
        Product deletedProduct;
        try {
            deletedProduct=this.productService.deleteProductFromDb(id);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(deletedProduct,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/products")
    public ResponseEntity<Product> updateProductInDb(@RequestBody UpdateProductDTO productDetails) throws ProductException {
        Product updatedProduct;
        try {
            updatedProduct=this.productService.updateProductInDb(productDetails);
        } catch (ProductException e) {
            throw new ProductException(e.getMessage());
        }
        return new ResponseEntity<>(updatedProduct,HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    @GetMapping("/orders/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable("customerId") Integer customerId) throws OrderException, CustomerException {
        return ResponseEntity.ok(this.orderService.getOrdersByCustomerId(customerId));
    }
}
