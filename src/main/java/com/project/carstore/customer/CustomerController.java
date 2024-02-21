package com.project.carstore.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/createCustomer")
    public Customer CreateCustomer(@RequestBody CustomerDTO customerDTO)
    {
        Customer customer=null;
        customer=this.customerService.AddCustomerToDb(customerDTO);
        return customer;
    }
}
