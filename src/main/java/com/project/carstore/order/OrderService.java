package com.project.carstore.order;

import com.project.carstore.cart.CartException;
import com.project.carstore.customer.Address;
import com.project.carstore.customer.AddressDto;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<StockValidationResponse> createOrder(Integer customerId) throws OrderException, CustomerException, CartException;

    Optional<Order> getOrderById(Integer orderId) throws OrderException;

    Order closeOrderById(Integer orderId) throws OrderException, CustomerException;

    ResponseEntity<Order> addAddressToOrder(Integer orderID, AddressDto newAddress) throws OrderException, CustomerException;

    Address getAddressByOrderId(Integer orderId) throws OrderException;

    List<Order> getOrdersByDate(LocalDate startDate, LocalDate endDate) throws OrderException;

    List<Order> getOrdersByCustomerId(Integer customerId) throws OrderException, CustomerException;

    Order updateOrderStatus(Integer orderId, String orderStatus) throws OrderException;

    List<Order> getOrdersByStatus(String orderStatus) throws OrderException;


    Double getTotalPrice(Integer orderId) throws OrderException;

    Order updatePaymentDetailsByOrderId(Integer orderId, PaymentDetails newPaymentDetails) throws OrderException;

    Order updateDeliveryDateByOrderId(Integer orderId, LocalDate newDeliveryDate) throws OrderException;


    ResponseEntity<Order> confirmOrder(ConfirmOrderReq confirmOrderReq) throws OrderException;

    List<Order> getAllOrders();
}