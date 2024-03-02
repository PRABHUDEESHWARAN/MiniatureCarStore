package com.project.carstore.customer;
import com.project.carstore.order.Order;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(nullable = false,length = 50)
    private String firstname;

    @Column(nullable = false,length = 50)
    private String lastname;

    @Column(nullable = false,length = 200)
    private String email;

    @Column(nullable = false,length = 15)
    private String password;

    @Column(nullable = false,length = 10)
    private Long mobileNo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address=new ArrayList<>();

    private Integer cartId;
    private Boolean isLoggedIn=false;

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> customerOrders =new ArrayList<>();

    public List<Order> getCustomerOrders() {
        return customerOrders;
    }


    public void setCustomerOrders(List<Order> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Customer(Integer userId,String firstname, String lastname, String email, String password, Long mobileNo) {
        this.userId=userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
    }

    public Customer() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
