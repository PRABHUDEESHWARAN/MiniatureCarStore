package com.project.carstore.customer;

public interface CustomerService {
    Customer AddCustomerToDb(CustomerDTO customerDTO);
    boolean existsById(Integer id);
    void save(CustomerDTO customer);
    void updateCustomer(Long id) throws Exception;

    String findByIdAndPassword();

    String findByIdAndPassword(Long id, String password);

}
