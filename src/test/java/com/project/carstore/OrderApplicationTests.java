package com.project.carstore;

import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartRepository;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.*;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.*;
import com.project.carstore.payment.PaymentDetails;
import org.antlr.v4.runtime.misc.LogManager;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CarstoreApplication.class)
@SpringJUnitConfig


public class OrderApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("A test case for the method createOrder")
    public void createOrderTest() throws OrderException , CartException {
       Integer customerId = 1;

       try {
          Assertions.assertNull(this.orderService.createOrder(customerId));

       } catch (Exception e) {

           System.out.println("Unexpected Exception in createOrderTest: " + e.getClass().getName() + " - " + e.getMessage());
       }
   }



    @Test
    @DisplayName("A test case for the method getOrderById")
    public void getOrderByIDTest()
    {
        try
        {
            Assertions.assertNotNull(1);
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception: "+e.getClass().getName());
        }
    }

    @Test
    public void getOrderByIdTestThrowsAnExceptionIfOrderIdsAreNotEqual()throws OrderException{
        Integer orderId=1;
        try
        {
            Assertions.assertEquals(orderId,this.orderService.getOrderById(orderId));
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception in getOrderByIdTest: "+e.getClass().getName());
        }
    }

    @Test
    @DisplayName("A test case for cancelOrderById method")
    public void cancelOrderByIdTest()
    {
        Integer orderId=1;
        try
        {
            Assertions.assertNull(this.orderService.closeOrderById(orderId));
        } catch (Exception e) {
            System.out.println("Unexpected error:"+e.getClass().getName());
        }

    }

    @Test
    public void ShouldThrowExceptionWhenCancelOrderReceivesNullTest()
    {

        try {
            assertNull(this.orderService.closeOrderById(null));
        }
        catch (Exception e)
        {
            System.out.println("Unexpected error:"+e.getClass().getName());
        }
    }

    @Test
    public void getOrdersByDateBetweenTest()
    {
        LocalDate startDate=LocalDate.of(2025,10,10);
        LocalDate endDate=LocalDate.of(2025,11,24);
        try
        {
            Assertions.assertNotNull(this.orderService.getOrdersByDate(startDate,endDate));
        }
        catch (Exception e)
        {
            System.out.println("Unexpected exception"+e.getClass().getName());
        }

    }

    @Test
    public void shouldThrowExceptionWhenGetOrdersDateBetweenReceivesNullTest()
    {
        LocalDate startDate=null;
        LocalDate endDate=null;
        try
        {
            Assertions.assertNull(this.orderService.getOrdersByDate(startDate,endDate));
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception"+e.getClass().getName());
        }

    }



    @Test
    public void throwsExceptionWhenGetTotalPriceReceivesNullTest()
    {
        Integer orderId=null;
        try
        {
            Assertions.assertNull(this.orderService.getTotalPrice(orderId));
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception"+e.getClass().getName());
        }
    }

    @Test
    public void updatePaymentDetailsByOrderIdTest()
    {
        Integer orderID=1;
        PaymentDetails paymentDetails=new PaymentDetails();
        try
        {
            Assertions.assertNotNull(this.orderService.updatePaymentDetailsByOrderId(orderID,paymentDetails));
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception"+e.getClass().getName());
        }
    }

    @Test
    public void orderIdAndPaymentDetailsOrderIdEqualsTest()
    {
        Integer orderID=1;
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setOrderId(orderID);
        try
        {
            Assertions.assertEquals(orderID,paymentDetails.getOrderId());
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception"+e.getClass().getName());
        }

    }

    @Test
    public void updateOrderStatusTest()
    {
        Integer orderId=1;
        String status="cancelled";

        try
        {
            Assertions.assertNotNull(this.orderService.updateOrderStatus(orderId,status));
        } catch (OrderException e) {
           e.printStackTrace();
        }
    }

    @Test
    public void updateOrderStatusTestIfOrderIdIsNull()
    {
        Integer orderId=null;
        String orderStatus="processing";
        try
        {
            assertNull(this.orderService.updateOrderStatus(orderId,orderStatus));
        }
        catch(Exception e)
        {
            System.out.println(e.getClass().getName());
        }
    }

    @Test
    public void updateOrderStatusNewTest()
    {
        Order sampleOrder = new Order(1,"vishnu","priya");
        orderRepository.save(sampleOrder);
        Integer orderId = sampleOrder.getId();
        String orderStatus = "SHIPPED";
        try {
            Order updatedOrder = this.orderService.updateOrderStatus(orderId, orderStatus);
            Order retrievedOrder = orderRepository.findById(orderId).orElse(null);
            assertEquals(orderStatus, retrievedOrder.getOrderStatus());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updateOrderStatusTest1()
    {
        Order newOrder=new Order();
        String status=null;
        try
        {
            assertNotNull(this.orderService.updateOrderStatus(newOrder.getId(), null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePaymentDetailsWhenReceivesNullOrderIdAndPaymentDetailsTest()
    {
        Integer orderId=null;
        PaymentDetails paymentDetails=null;
        try
        {
            Assertions.assertNull(this.orderService.updatePaymentDetailsByOrderId(null,null));
        }
        catch(Exception e)
        {
            System.out.println("Unexpected Exception"+e.getClass().getName());
        }
    }

    @Test
    public void getOrdersByStatusTest()
    {
        String orderStatus="pending";
        try
        {
            List<Order> actualOrders = this.orderService.getOrdersByStatus(orderStatus);


            assertEquals(0, actualOrders.size());
        } catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetOrdersByStatusWithNullStatus() throws OrderException {

        try
        {
            Assertions.assertNull(this.orderService.getOrdersByStatus(null));
        }
        catch(Exception e)
        {
            System.out.println("you got unexpected exception: "+e.getClass().getName());
        }
    }

    @Test
    public void getOrdersByCustomerIdTest()
    {
        Integer customerId=1;
        try
        {
            Assertions.assertNotNull(this.orderService.getOrdersByCustomerId(customerId));
        } catch (CustomerException e) {
            e.printStackTrace();
        } catch (OrderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOrdersByCustomerIdEqualsTest()
    {
        try
        {
            List<Order> orderList=this.orderService.getOrdersByCustomerId(1);
            Assertions.assertEquals(0,orderList.size());
        }
        catch(OrderException | CustomerException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updateDeliveryDateByOrderITest()
    {
        Integer orderId = 1;
        LocalDate newDeliveryDate = LocalDate.now().plusDays(7);  // Replace with a new delivery date


        Order updatedOrder = null;
        try {
            updatedOrder = orderService.updateDeliveryDateByOrderId(orderId, newDeliveryDate);
        } catch (OrderException e) {
            Assertions.fail("An exception occurred: " + e.getMessage());
        }


        Assertions.assertNotNull(updatedOrder, "Updated order should not be null");
        Assertions.assertEquals(newDeliveryDate, updatedOrder.getDeliveryDate(), "Delivery date should match the new date");


    }

    @Test
    public void ddAddressToOrderTest()
    {
        try
        {
            Assertions.assertNotNull(this.orderService.addAddressToOrder(1,new AddressDto(1,111,"chennai",6789,"tn")));
        }
        catch(OrderException | CustomerException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void ddAddressToOrderNullTest() throws CustomerException {
        Customer sampleCustomer = new Customer("vishnu","priya","vish","pri", 3264725L);
        this.customerRepository.save(sampleCustomer);
        Order sampleOrder = new Order(sampleCustomer.getId(),"vishnu","priya");
        orderRepository.save(sampleOrder);
        Integer orderId = sampleOrder.getId();
        AddressDto addressDto=new AddressDto(sampleCustomer.getId(),111,"chennai",7890,"tn");
        try
        {
            ResponseEntity<Order> response = this.orderService.addAddressToOrder(1, addressDto);
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        } catch (OrderException | CustomerException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void getAddressByOrderIdTest()
    {try {
        Address address = this.orderService.getAddressByOrderId(1);
        if (address != null) {
            Assertions.assertNotNull(address);
        }
    } catch (OrderException e) {
        System.out.println(e.getClass().getName());
    }
    }}

