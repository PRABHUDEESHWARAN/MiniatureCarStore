package com.project.carstore.customer;

import com.project.carstore.cart.Cart;
import com.project.carstore.cart.CartRepository;
import com.project.carstore.cart.CartService;
import com.project.carstore.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;
@Service

public class CustomerServiceImp implements CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartService cartService;

    @Override
    public Customer AddCustomerToDb(CustomerDTO customerDTO) {

        Customer newCustomer =new Customer(customerDTO.getFirstname(),customerDTO.getLastname(),customerDTO.getEmail(),customerDTO.getPassword(),customerDTO.getMobileNo());
        newCustomer=this.customerRepository.save(newCustomer);
        Integer newCartId=this.cartService.CreateCartForCustomer(newCustomer.getId());
        newCustomer.setCartId(newCartId);
        return this.customerRepository.save(newCustomer);
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return this.customerRepository.findById(id);
    }

    @Override
    public void addOrderToCustomer(Order newOrder) {
        Optional<Customer> findCustomer=this.getCustomerById(newOrder.getCustomerId());
        if(findCustomer.isPresent())
        {
            List<Order> customerOrders=findCustomer.get().getCustomerOrders();
            customerOrders.add(newOrder);
            findCustomer.get().setCustomerOrders(customerOrders);
            this.customerRepository.save(findCustomer.get());
        }
    }
}



