package com.project.carstore.customer;

import java.util.Optional;

public interface CustomerService {
    Customer AddCustomerToDb(CustomerDTO customerDTO);
    Optional<Customer> getCustomerById(Integer id);

}
