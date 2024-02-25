package com.project.carstore.customer;

import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/createCustomer")
    public Customer CreateCustomer(@RequestBody CustomerDto customerDTO) throws CustomerException, CartException {
        Customer customer=null;
        customer=this.customerService.AddCustomerToDb(customerDTO);
        return customer;
    }
    @PostMapping("/update/email")
    public String UpdateCustomerEmail(@RequestBody UpdateEmailDto updateEmailDto) throws CustomerException {
        return this.customerService.UpdateCustomerEmail(updateEmailDto);
    }
    @PostMapping("/update/pwd")
    public String UpdateCustomerPwd(@RequestBody UpdatePwdDto updatePwdDto) throws CustomerException{
        return this.customerService.UpdateCustomerPwd(updatePwdDto);
    }
    @PostMapping("/orders/{customerId}")
    public List<Order> getCustomerOrders(@PathVariable() Integer customerId) throws CustomerException
    {
        return this.customerService.getCustomerOrders(customerId);
    }
    @PostMapping("/address")
    public String AddCustomerAddress(@RequestBody AddressDto addressDto) throws CustomerException {
        return this.customerService.addAddressToCustomer(addressDto);
    }
    @PostMapping("/address/{customerId}")
    public List<Address> getCustomerAddress(@PathVariable Integer customerId) throws CustomerException{
        return this.customerService.getCustomerAddress(customerId);
    }

}
