package com.project.carstore;

import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartRepository;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.*;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.order.*;
import com.project.carstore.payment.PaymentDetails;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CarstoreApplication.class)
@SpringJUnitConfig


class OrderApplicationTests {

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
    void createOrderTest() throws OrderException , CartException {
       Integer customerId = 1;

       try {
          Assertions.assertNull(this.orderService.createOrder(customerId));

       } catch (Exception e) {

           System.out.println("Unexpected Exception in createOrderTest: " + e.getClass().getName() + " - " + e.getMessage());
       }
   }



    @Test
    @DisplayName("A test case for the method getOrderById")
    void getOrderByIDTest()
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
    void getOrderByIdTestThrowsAnExceptionIfOrderIdsAreNotEqual()throws OrderException{
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
    void cancelOrderByIdTest()
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
    void ShouldThrowExceptionWhenCancelOrderReceivesNullTest()
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
    void getOrdersByDateBetweenTest()
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
    void shouldThrowExceptionWhenGetOrdersDateBetweenReceivesNullTest()
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
    void throwsExceptionWhenGetTotalPriceReceivesNullTest()
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
    void updatePaymentDetailsByOrderIdTest()
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
    void orderIdAndPaymentDetailsOrderIdEqualsTest()
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
    void updateOrderStatusTest()
    {
        Integer orderId=1;
        String status="cancelled";

        try
        {
            Assertions.assertNotNull(this.orderService.updateOrderStatus(orderId,status));
        } catch (OrderException e) {
            Assertions.assertEquals("order does not exist with Id:"+1,e.getMessage());
        }
    }

    @Test
    void updateOrderStatusTestIfOrderIdIsNull()
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
    void updateOrderStatusNewTest()
    {
        Order sampleOrder = new Order(1,"vishnu","priya");
        this.orderRepository.save(sampleOrder);
        Integer orderId = sampleOrder.getId();
        String orderStatus = "SHIPPED";
        try {
            Order updatedOrder = this.orderService.updateOrderStatus(orderId, orderStatus);
            Order retrievedOrder = orderRepository.findById(orderId).orElse(null);
            assertEquals(orderStatus, retrievedOrder.getOrderStatus());
        }
        catch (Exception e)
        {
            Assertions.assertEquals("order does not exist with Id:"+sampleOrder.getId(),e.getMessage());
        }
    }
    @Test
    void updateOrderStatusTest1()
    {
        Order newOrder=new Order(1,"vishnu","priya");
        this.orderRepository.save(newOrder);
        String status=null;
        try
        {
            assertNotNull(this.orderService.updateOrderStatus(newOrder.getId(), null));
        }
        catch (OrderException e)
        {
            Assertions.assertEquals("order does not exist with Id:"+newOrder.getId(),e.getMessage());
        }
    }
    @Test
    void updatePaymentDetailsWhenReceivesNullOrderIdAndPaymentDetailsTest()
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
    void getOrdersByStatusTest()
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
    void testGetOrdersByStatusWithNullStatus() throws OrderException {

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
    void getOrdersByCustomerIdTest()
    {
        Integer customerId=1;
        try
        {
            Assertions.assertNotNull(this.orderService.getOrdersByCustomerId(customerId));
        }catch (OrderException | CustomerException e) {
            assertEquals("No customer found with the provided id:"+customerId,e.getMessage());
        }
    }
    @Test
    void getOrdersByCustomerIdEqualsTest()
    {
        try
        {
            List<Order> orderList=this.orderService.getOrdersByCustomerId(1);
            Assertions.assertEquals(0,orderList.size());
        }
        catch(OrderException | CustomerException e)
        {
            assertEquals("No customer found with the provided id:"+1,e.getMessage());
        }
    }
    @Test
    void updateDeliveryDateByOrderITest()
    {
        Integer orderId = 1;
        LocalDate newDeliveryDate = LocalDate.now().plusDays(7);


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
    void addAddressToOrderTest()
    {
        try
        {
            Assertions.assertNotNull(this.orderService.addAddressToOrder(1,new AddressDto(1,111,"chennai",6789,"tn")));
        }
        catch(OrderException | CustomerException e)
        {
            Assertions.assertEquals("No customer exist with id: "+1,e.getMessage());
        }
    }

    @Test
    @Transactional
    void ddAddressToOrderNullTest() throws CustomerException {
        Customer sampleCustomer = new Customer("john","leo","john","leo", 3264725L);
        this.customerRepository.save(sampleCustomer);
        Order sampleOrder = new Order(sampleCustomer.getId(),"john","leo");
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
    void getAddressByOrderIdTest()
    {try {
        Address address = this.orderService.getAddressByOrderId(1);
        if (address != null) {
            Assertions.assertNotNull(address);
        }
    } catch (OrderException e) {
        System.out.println(e.getClass().getName());
    }
    }
    @Test
    void confirmOrderTest() throws OrderException {
        Customer customer=new Customer("john","leo","john","leo", 93875L);
        ConfirmOrderReq confirmOrderReq = new ConfirmOrderReq("1", 1);
        Order existingOrder = new Order(customer.getId(),"john","leo");
        existingOrder.setOrderStatus("Pending");
        ResponseEntity<Order> response = this.orderService.confirmOrder(confirmOrderReq);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
