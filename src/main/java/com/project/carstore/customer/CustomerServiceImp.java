package com.project.carstore.customer;
import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartService;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.Order;
import com.project.carstore.security.User;
import com.project.carstore.security.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Optional;
@Service
public class CustomerServiceImp implements CustomerService
{
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    public CustomerServiceImp(CustomerRepository customerRepository, UserRepository  userRepository,AddressRepository addressRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.cartService = cartService;
        this.userRepository=userRepository;
    }
    @Override
    public Customer addCustomerToDb(CustomerDto customerDTO) throws  CartException {
        Customer newCustomer =new Customer(customerDTO.getUserId(),customerDTO.getFirstname(),customerDTO.getLastname(),customerDTO.getEmail(),customerDTO.getPassword(),customerDTO.getMobileNo());
        newCustomer=this.customerRepository.save(newCustomer);
        Integer newCartId=this.cartService.createCartForCustomer(newCustomer.getId());
        newCustomer.setCartId(newCartId);
        return this.customerRepository.save(newCustomer);
    }
    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return this.customerRepository.findById(id);
    }
    @Override
    public void addOrderToCustomer(Order newOrder) {
        Optional<Customer> foundCustomer=this.getCustomerById(newOrder.getCustomerId());
        if(foundCustomer.isPresent())
        {
            List<Order> customerOrders=foundCustomer.get().getCustomerOrders();
            customerOrders.add(newOrder);
            foundCustomer.get().setCustomerOrders(customerOrders);
            this.customerRepository.save(foundCustomer.get());
        }
    }

    @Override
    public String updateCustomerEmail(UpdateEmailDto updateEmailDto) throws CustomerException {
        Optional<Customer> foundCustomer=this.customerRepository.findById(updateEmailDto.getCustomerId());
        if(foundCustomer.isPresent())
        {
            foundCustomer.get().setEmail(updateEmailDto.getNewEmail());
            this.customerRepository.save(foundCustomer.get());
            return "The Customer's email has been updated Successfully";
        }
        else {
            throw new CustomerException("No Customer exists with id: "+updateEmailDto.getCustomerId());
        }
    }

    @Override
    public String updateCustomerPwd(UpdatePwdDto updatePwdDto) throws CustomerException {
        Optional<Customer> foundCustomer=this.customerRepository.findById(updatePwdDto.getCustomerId());
        if(foundCustomer.isPresent())
        {
            if(foundCustomer.get().getPassword().equals(updatePwdDto.getOldPassword()))
            {
                foundCustomer.get().setPassword(updatePwdDto.getNewPassword());
                this.customerRepository.save(foundCustomer.get());
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
    public String deleteCustomerById(Integer customerId) throws CustomerException {
        Optional<Customer> foundCustomer = this.customerRepository.findById(customerId);
        if(foundCustomer.isPresent())
        {
            this.customerRepository.delete(foundCustomer.get());
            Optional<User> userOpt=this.userRepository.findById(foundCustomer.get().getUserId());
            if(userOpt.isEmpty())throw new CustomerException("User not Found");
            this.userRepository.delete(userOpt.get());
            return "The Account has been deleted Successfully👍";
        }
        else {
            throw new CustomerException("No Customer exists with Id:"+customerId);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerException {
        List<Customer> customerList= this.customerRepository.findAll();
        if(customerList.isEmpty())
        {
            throw new CustomerException("No customer exists in the database");
        }
        return customerList;
    }

    @Override
    public Customer getCustomerByEmail(String email) throws CustomerException {
        Optional<Customer> foundCustomer= this.customerRepository.findCustomerByEmail(email);
        if(foundCustomer.isPresent())
        {
            return foundCustomer.get();
        }
        else throw new CustomerException("No Customer exists with Email: "+email);
    }

    @Override
    public Customer getCustomerByMobileNo(Long mobileNo) throws CustomerException {
        Optional<Customer>foundCustomer= this.customerRepository.findCustomerByMobileNo(mobileNo);
        if(foundCustomer.isPresent()){
            return foundCustomer.get();
        }
        else {
            throw new CustomerException("No Customer exists with MobileNo: "+mobileNo);
        }
    }

    @Override
    public List<Order> getCustomerOrders(Integer customerId) throws CustomerException {
        Optional<Customer> foundCustomer=this.customerRepository.findById(customerId);
        if(foundCustomer.isPresent())
        {
            return foundCustomer.get().getCustomerOrders();
        }
        else{
            throw new CustomerException("No Customer exists with Id: "+customerId);
        }
    }

    @Override
    public String addAddressToCustomer(AddressDto addressDto) throws CustomerException {
        //get customer using customer id
        Optional<Customer> foundCustomer=this.customerRepository.findById(addressDto.getCustomerId());
        //create address object
        if(foundCustomer.isPresent())
        {
            Address newAddress=new Address(addressDto.getCustomerId(), addressDto.getDoorNo(),addressDto.getCity(),addressDto.getPincode(),addressDto.getState());
            this.addressRepository.save(newAddress);
            List<Address> addressList=foundCustomer.get().getAddress();
            addressList.add(newAddress);
            foundCustomer.get().setAddress(addressList);
            this.customerRepository.save(foundCustomer.get());
            return "Address added Successfully";
        }
        else {
            throw new CustomerException("No customer exist with id: " + addressDto.getCustomerId());
        }
    }

    @Override
    public List<Address> getCustomerAddress(Integer id) throws CustomerException {
        Optional<Customer> foundCustomer=this.customerRepository.findById(id);
        //create address object
        if(foundCustomer.isPresent())
        {
            return foundCustomer.get().getAddress();
        }
        else {
            throw new CustomerException("No customer exist with id: " + id);
        }
    }


}



