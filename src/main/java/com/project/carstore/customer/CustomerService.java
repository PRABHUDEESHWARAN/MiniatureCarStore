package com.project.carstore.customer;

import com.project.carstore.order.Order;

import java.util.Optional;

public interface CustomerService {
    Customer AddCustomerToDb(CustomerDTO customerDTO);
    Optional<Customer> getCustomerById(Integer id);

    void addOrderToCustomer(Order newOrder);
}
