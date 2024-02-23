package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.payment.PaymentDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Integer customerId) throws OrderException;
    List<Order> addOrderToCustomerOrdersList(Order newOrder) throws OrderException;
    Order getOrderById(Integer id) throws OrderException;
    Order deleteOrderById(Integer id);


 Order updateOrder(Order newOrder);

 Double getTotalPrices(List<OrderItem> orderItems) throws OrderException;
    LocalDate getOrderDate();
    Boolean paymentStatus(PaymentDetails paymentDetails) throws OrderException;
    String getOrderStatusById(Integer id) throws OrderException;
    LocalDate getDeliveryDateByOrderId(Order order) throws OrderException;
    Integer getTotalNumberOfItems(List<OrderItem> orderItems) throws OrderException;
    Boolean checkAvailabilityOfOrderItem(Order order);


}
