package com.project.carstore.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    CustomerRepository customerRepository;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/registerCustomer")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customer) {
        try {
            if (customerService.existsById(customer.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer ID already exists");
            }
            customerService.save(customer);
            return ResponseEntity.status(HttpStatus.OK).body("Customer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering customer");
        }
    }
    @PostMapping("/loginCustomer")
    public ResponseEntity<String> loginCustomer(@RequestBody CustomerService customerservice) {
        try {
            Customer customer = customerService.findByIdAndPassword(Customer.getId(), Customer.getPassword());
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or password");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging in customer");
        }
    }
    @PutMapping
    public ResponseEntity<String> updateCustomerDetails(@PathVariable Long id) {
        try {
            customerService.updateCustomer(id);
            return ResponseEntity.ok("Customer details updated successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
