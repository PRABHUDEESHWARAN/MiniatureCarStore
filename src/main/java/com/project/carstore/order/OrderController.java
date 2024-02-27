package com.project.carstore.order;

import com.project.carstore.cart.CartException;
import com.project.carstore.customer.Address;
import com.project.carstore.customer.AddressDto;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController

public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{customerId}")
    public ResponseEntity<StockValidationResponse> createOrder(@PathVariable("customerId") Integer customerId) throws OrderException, CustomerException, CartException {
        return this.orderService.createOrder(customerId);
    }

    @GetMapping("getOrder/{id}")
    public Optional<Order> getOrderById(@PathVariable("id") Integer id) throws OrderException {
        return this.orderService.getOrderById(id);
    }

    @DeleteMapping("closeOrder/{orderId}")
    public Order deleteOrderById(@PathVariable("orderId") Integer orderId) throws OrderException, CustomerException {
        return this.orderService.closeOrderById(orderId);
    }

    @PostMapping("addAddress/{orderId}")
    public ResponseEntity<Order> addAddressByOrderID(@PathVariable("orderId") Integer orderId, @RequestBody AddressDto newAddress) throws OrderException, CustomerException {
        return this.orderService.addAddressToOrder(orderId,newAddress);
    }

    @GetMapping("getAddressByOrderId/{orderId}")
    public Address getAddressByOrderID(@PathVariable("orderId") Integer orderID) throws OrderException {
        return this.orderService.getAddressByOrderId(orderID);
    }

    @GetMapping("getTotalPriceById/{orderId}")
    public Double getTotalPriceById(@PathVariable("orderId") Integer orderId) throws OrderException {
        return this.orderService.getTotalPrice(orderId);
    }

    @PatchMapping("updatePaymentDetails/{orderId}")
    public Order updatePaymentDetails(@PathVariable("orderId") Integer orderId,@RequestBody PaymentDetails newPaymentDetails) throws OrderException {
        return this.orderService.updatePaymentDetailsByOrderId(orderId,newPaymentDetails);
    }

    @GetMapping("getOrdersByDate/{startDate}/{endDate}")
    public List<Order> getOrdersByDateBetween(@PathVariable("startDate") LocalDate startDate,@PathVariable LocalDate endDate) throws OrderException {
        return  this.orderService.getOrdersByDate(startDate, endDate);
    }

    @PatchMapping("updateDeliveryDateByOrderId/{orderId}")
    public Order updateDeliveryDateByOrderId(@PathVariable("orderId") Integer orderId,@RequestBody LocalDate newDeliveryDate) throws OrderException {
        return this.orderService.updateDeliveryDateByOrderId(orderId,newDeliveryDate);
    }

    @GetMapping("getOrdersByCustomerId/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Integer customerId) throws OrderException, CustomerException {
        return this.orderService.getOrdersByCustomerId(customerId);
    }

    @PatchMapping("updateOrderStatusByOrderId/{orderId}")
    public Order updateOrderStatusByOrderId(@PathVariable("orderId") Integer orderId,@RequestBody String orderStatus) throws OrderException {
        return this.orderService.updateOrderStatus(orderId,orderStatus);
    }

    @GetMapping("getOrdersByStatus/{status}")
    public List<Order>  getOrdersByStatus(@PathVariable("status") String status) throws OrderException {
        return this.orderService.getOrdersByStatus(status);
    }

    @PutMapping("/confirmOrder")
        public ResponseEntity<Order> confirmOrder(@RequestBody ConfirmOrderReq confirmOrderReq) throws OrderException {
            return this.orderService.confirmOrder(confirmOrderReq);
        }




}