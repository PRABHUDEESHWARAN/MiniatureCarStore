package com.project.carstore.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer AddCustomerToDb(CustomerDTO customerDTO) {
        return this.customerRepository.save(new Customer(customerDTO.getId(),customerDTO.getFirstname(),customerDTO.getLastname(),customerDTO.getEmail(),customerDTO.getPassword(),customerDTO.getMobileNo()));
    }
}
