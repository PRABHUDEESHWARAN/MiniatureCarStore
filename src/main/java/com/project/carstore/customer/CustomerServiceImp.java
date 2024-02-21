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
    private final CustomerRepository customerRepository;
    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void save(CustomerDTO customer) {

    }


    public void updateCustomer(Long Id) throws Exception {
        Customer customer = CustomerRepository.findById(id);
        customer.setname(customer.getname());
        customer.setEmail(customer.getEmail());
    }

    @Override
    public String findByIdAndPassword() {
        return findByIdAndPassword(null, null);
    }

    @Override
    public String findByIdAndPassword(Long id, String password) {
        return null;
    }
}