package com.project.carstore.customer;
import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.Order;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/new")
    public Customer createCustomer(@RequestBody CustomerDto customerDTO) throws CustomerException, CartException {
        Customer customer;
        customer=this.customerService.addCustomerToDb(customerDTO);
        return customer;
    }
    @PostMapping("/email")
    public String updateCustomerEmail(@RequestBody UpdateEmailDto updateEmailDto) throws CustomerException {
        return this.customerService.updateCustomerEmail(updateEmailDto);
    }
    @PostMapping("/pwd")
    public String updateCustomerPwd(@RequestBody UpdatePwdDto updatePwdDto) throws CustomerException{
        return this.customerService.updateCustomerPwd(updatePwdDto);
    }
    @PostMapping("/orders/{customerId}")
    public List<Order> getCustomerOrders(@PathVariable() Integer customerId) throws CustomerException
    {
        return this.customerService.getCustomerOrders(customerId);
    }
    @PostMapping("/address")
    public String addCustomerAddress(@RequestBody AddressDto addressDto) throws CustomerException {
        return this.customerService.addAddressToCustomer(addressDto);
    }
    @PostMapping("/addresses/{customerId}")
    public List<Address> getCustomerAddress(@PathVariable Integer customerId) throws CustomerException{
        return this.customerService.getCustomerAddress(customerId);
    }

}
