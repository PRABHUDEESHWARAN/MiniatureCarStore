package com.project.carstore.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class CustomerServiceImp implements CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Customer AddCustomerToDb(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return Optional.empty();
    }
}



