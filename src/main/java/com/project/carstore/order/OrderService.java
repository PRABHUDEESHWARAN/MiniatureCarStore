package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.payment.PaymentDetails;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createOrder(Integer customerId) throws OrderException;

    Order getOrderById(Integer orderId) throws OrderException;
    Order cancelOrderById(Integer orderId) throws OrderException;

    Address addAddressToOrder(Integer orderID,Address newAddress ) throws OrderException;
    Address getAddressByOrderId(Integer orderId) throws OrderException;
    List<Order> getOrdersByDate(LocalDate startDate,LocalDate endDate) throws OrderException;
    List<Order> getOrdersByCustomerId(Integer customerId)throws OrderException;
    Order updateOrderStatus(Integer orderId,String orderStatus) throws OrderException;
    List<Order> getOrdersByStatus(String orderStatus) throws OrderException;



    Double getTotalPrice(Integer orderId) throws OrderException;
    Order updatePaymentDetailsByOrderId(Integer orderId,PaymentDetails newPaymentDetails) throws OrderException;
    Order updateDeliveryDateByOrderId(Integer orderId,LocalDate newDeliveryDate) throws OrderException;




}