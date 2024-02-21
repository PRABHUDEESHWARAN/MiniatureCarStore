package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.payment.PaymentDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(CustomerDTO customerDto, List<OrderItem> orderItems, PaymentDetails paymentDetails);
    List<Order> addOrderToCustomerOrdersList(Order newOrder);
    Order getOrderById(Integer id) throws OrderException;
    Order deleteOrderById(Integer id);


 Order updateOrder(Order newOrder);

 Integer getTotalPrices(List<OrderItem> orderItems);
    LocalDate getOrderDate();
    Boolean paymentStatus(PaymentDetails paymentDetails);
    String getOrderStatusById(Integer id) throws OrderException;
    LocalDate getDeliveryDateByOrderId(Order order);
    Integer getTotalNumberOfItems(List<OrderItem> orderItems);


}
