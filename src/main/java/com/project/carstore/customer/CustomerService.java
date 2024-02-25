package com.project.carstore.customer;

import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.Order;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer AddCustomerToDb(CustomerDto customerDTO) throws CustomerException, CartException;
    Optional<Customer> getCustomerById(Integer id) throws CustomerException;

    void addOrderToCustomer(Order newOrder) throws CustomerException;

    String UpdateCustomerEmail(UpdateEmailDto updateEmailDto) throws CustomerException;

    String UpdateCustomerPwd(UpdatePwdDto updatePwdDto) throws CustomerException;
     String DeleteCustomerById(Integer customerId) throws CustomerException;
     List<Customer> GetAllCustomers() throws CustomerException;
     Customer GetCustomerByEmail(String email) throws CustomerException;
     Customer getCustomerByMobileNo(Long mobileNo) throws CustomerException;

    List<Order> getCustomerOrders(Integer customerId) throws CustomerException;

    String addAddressToCustomer(AddressDto addressDto) throws CustomerException;
    List<Address> getCustomerAddress(Integer id) throws CustomerException;
}
