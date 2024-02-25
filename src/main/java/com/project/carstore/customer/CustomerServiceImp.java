package com.project.carstore.customer;

import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartRepository;
import com.project.carstore.cart.CartService;
import com.project.carstore.exceptions.CustomerException;
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
    public Customer AddCustomerToDb(CustomerDto customerDTO) throws CustomerException, CartException {

        Customer newCustomer =new Customer(customerDTO.getFirstname(),customerDTO.getLastname(),customerDTO.getEmail(),customerDTO.getPassword(),customerDTO.getMobileNo());
        newCustomer=this.customerRepository.save(newCustomer);
        Integer newCartId=this.cartService.CreateCartForCustomer(newCustomer.getId());
        newCustomer.setCartId(newCartId);
        return this.customerRepository.save(newCustomer);
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) throws CustomerException{
        return this.customerRepository.findById(id);
    }

    @Override
    public void addOrderToCustomer(Order newOrder) throws CustomerException {
        Optional<Customer> findCustomer=this.getCustomerById(newOrder.getCustomerId());
        if(findCustomer.isPresent())
        {
            List<Order> customerOrders=findCustomer.get().getCustomerOrders();
            customerOrders.add(newOrder);
            findCustomer.get().setCustomerOrders(customerOrders);
            this.customerRepository.save(findCustomer.get());
        }
    }

    @Override
    public String UpdateCustomerEmail(UpdateEmailDto updateEmailDto) throws CustomerException {
        Optional<Customer> findCustomer=this.customerRepository.findById(updateEmailDto.getCustomerId());
        if(findCustomer.isPresent())
        {
            findCustomer.get().setEmail(updateEmailDto.getNewEmail());
            this.customerRepository.save(findCustomer.get());
            return "The Customer's email has been updated Successfully";
        }
        else {
            throw new CustomerException("No Customer exists with id: "+updateEmailDto.getCustomerId());
        }
    }

    @Override
    public String UpdateCustomerPwd(UpdatePwdDto updatePwdDto) throws CustomerException {
        Optional<Customer> findCustomer=this.customerRepository.findById(updatePwdDto.getCustomerId());
        if(findCustomer.isPresent())
        {
            if(findCustomer.get().getPassword().equals(updatePwdDto.getOldPassword()))
            {
                findCustomer.get().setPassword(updatePwdDto.getNewPassword());
                this.customerRepository.save(findCustomer.get());
                return "Password changed Successfully";
            }
            else {
                return "The Old password did not match. Pls try again!!!";
            }
        }
        else {
            throw new CustomerException("No Customer exists with id: "+updatePwdDto.getCustomerId());
        }
    }

    @Override
    public String DeleteCustomerById(Integer customerId) throws CustomerException {
        Optional<Customer> findCustomer = this.customerRepository.findById(customerId);
        if(findCustomer.isPresent())
        {
            this.customerRepository.delete(findCustomer.get());
            return "The Account has been deleted Successfully👍";
        }
        else {
            throw new CustomerException("No Customer exists with Id:"+customerId);
        }
    }

    @Override
    public List<Customer> GetAllCustomers() throws CustomerException {
        List<Customer> customerList= this.customerRepository.findAll();
        if(customerList.isEmpty())
        {
            throw new CustomerException("No customer exists in the database");
        }
        return customerList;
    }

    @Override
    public Customer GetCustomerByEmail(String email) throws CustomerException {
        Optional<Customer> findCustomer= this.customerRepository.findCustomerByEmail(email);
        if(findCustomer.isPresent())
        {
            return findCustomer.get();
        }
        else throw new CustomerException("No Customer exists with Email: "+email);
    }

    @Override
    public Customer getCustomerByMobileNo(Long mobileNo) throws CustomerException {
        Optional<Customer>findCustomer= this.customerRepository.findCustomerByMobileNo(mobileNo);
        if(findCustomer.isPresent()){
            return findCustomer.get();
        }
        else {
            throw new CustomerException("No Customer exists with MobileNo: "+mobileNo);
        }
    }

    @Override
    public List<Order> getCustomerOrders(Integer customerId) throws CustomerException {
        Optional<Customer> findCustomer=this.customerRepository.findById(customerId);
        if(findCustomer.isPresent())
        {
            return findCustomer.get().getCustomerOrders();
        }
        else{
            throw new CustomerException("No Customer exists with Id: "+customerId);
        }
    }

    @Override
    public String addAddressToCustomer(AddressDto addressDto) throws CustomerException {
        //get customer using customer id
        Optional<Customer> findCustomer=this.customerRepository.findById(addressDto.getCustomerId());
        //create address object
        if(findCustomer.isPresent())
        {
            Address newAddress=new Address(addressDto.getCustomerId(), addressDto.getDoorNo(),addressDto.getCity(),addressDto.getPincode(),addressDto.getState());
            this.addressRepository.save(newAddress);
            List<Address> addressList=findCustomer.get().getAddress();
            addressList.add(newAddress);
            findCustomer.get().setAddress(addressList);
            this.customerRepository.save(findCustomer.get());
            return "Address added Successfully";
        }
        else {
            throw new CustomerException("No customer exist with id: " + addressDto.getCustomerId());
        }
    }

    @Override
    public List<Address> getCustomerAddress(Integer id) throws CustomerException {
        Optional<Customer> findCustomer=this.customerRepository.findById(id);
        //create address object
        if(findCustomer.isPresent())
        {
            return findCustomer.get().getAddress();
        }
        else {
            throw new CustomerException("No customer exist with id: " + id);
        }
    }


}



