package com.project.carstore.customer;

import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.Order;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer addCustomerToDb(CustomerDto customerDTO) throws CustomerException, CartException;
    Optional<Customer> getCustomerById(Integer id) throws CustomerException;

    void addOrderToCustomer(Order newOrder) throws CustomerException;

    String updateCustomerEmail(UpdateEmailDto updateEmailDto) throws CustomerException;

    String updateCustomerPwd(UpdatePwdDto updatePwdDto) throws CustomerException;
     String deleteCustomerById(Integer customerId) throws CustomerException;
     List<Customer> getAllCustomers() throws CustomerException;
     Customer getCustomerByEmail(String email) throws CustomerException;
     Customer getCustomerByMobileNo(Long mobileNo) throws CustomerException;

    List<Order> getCustomerOrders(Integer customerId) throws CustomerException;

    String addAddressToCustomer(AddressDto addressDto) throws CustomerException;
    List<Address> getCustomerAddress(Integer id) throws CustomerException;
}
