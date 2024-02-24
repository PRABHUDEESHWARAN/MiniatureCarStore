package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.payment.PaymentDetails;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController

public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{customerId}")
    public Order createOrder(@PathVariable("customerId") Integer customerId) throws OrderException {
        return this.orderService.createOrder(customerId);
    }

    @GetMapping("getOrder/{id}")
    public Order getOrderById(@PathVariable("id") Integer id) throws OrderException {
        return this.orderService.getOrderById(id);
    }

    @DeleteMapping("cancelOrder/{orderId}")
    public Order deleteOrderById(@PathVariable("orderId") Integer orderId) throws OrderException {
        return this.orderService.cancelOrderById(orderId);
    }

    @PostMapping("addAddress/{orderId}")
    public Address addAddressByOrderID(@PathVariable("orderId") Integer orderId,@RequestBody Address newAddress) throws OrderException {
        return this.orderService.addAddressToOrder(orderId,newAddress);
    }

    @GetMapping("getAddressByOrderId/{orderId}")
    public Address getAddressByOrderID(@PathVariable("orderId") Integer orderID) throws OrderException {
        return this.orderService.getAddressByOrderId(orderID);
    }

    @GetMapping("getTotalAmountOfOrderByOrderID/{orderID}")
    public Double getTotalAmountOfOrderByOrderID(@PathVariable("orderID") Integer orderId) throws OrderException {
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
    public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Integer customerId) throws OrderException {
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




}