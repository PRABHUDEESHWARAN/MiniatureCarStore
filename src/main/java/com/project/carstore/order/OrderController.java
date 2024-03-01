package com.project.carstore.order;

import com.project.carstore.cart.CartException;
import com.project.carstore.customer.Address;
import com.project.carstore.customer.AddressDto;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/new/{customerId}")
    public ResponseEntity<StockValidationResponse> createOrder(@PathVariable("customerId") Integer customerId) throws OrderException, CustomerException, CartException {
        return this.orderService.createOrder(customerId);
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable("id") Integer id) throws OrderException {
        return this.orderService.getOrderById(id);
    }

    @DeleteMapping("/{orderId}")
    public Order deleteOrderById(@PathVariable("orderId") Integer orderId) throws OrderException, CustomerException {
        return this.orderService.closeOrderById(orderId);
    }

    @PostMapping("/address/{orderId}")
    public ResponseEntity<Order> addAddressByOrderID(@PathVariable("orderId") Integer orderId, @RequestBody AddressDto newAddress) throws OrderException, CustomerException {
        return this.orderService.addAddressToOrder(orderId, newAddress);
    }

    @GetMapping("/address/{orderId}")
    public Address getAddressByOrderID(@PathVariable("orderId") Integer orderID) throws OrderException {
        return this.orderService.getAddressByOrderId(orderID);
    }

    @GetMapping("/price/total/{orderId}")
    public Double getTotalPriceById(@PathVariable("orderId") Integer orderId) throws OrderException {
        return this.orderService.getTotalPrice(orderId);
    }

    @PatchMapping("/payment-details/{orderId}")
    public Order updatePaymentDetails(@PathVariable("orderId") Integer orderId, @RequestBody PaymentDetails newPaymentDetails) throws OrderException {
        return this.orderService.updatePaymentDetailsByOrderId(orderId, newPaymentDetails);
    }

    @GetMapping("/{startDate}/{endDate}")
    public List<Order> getOrdersByDateBetween(@PathVariable("startDate") LocalDate startDate, @PathVariable LocalDate endDate) throws OrderException {
        return this.orderService.getOrdersByDate(startDate, endDate);
    }

    @PatchMapping("/{orderId}")
    public Order updateDeliveryDateByOrderId(@PathVariable("orderId") Integer orderId, @RequestBody LocalDate newDeliveryDate) throws OrderException {
        return this.orderService.updateDeliveryDateByOrderId(orderId, newDeliveryDate);
    }

    @GetMapping("/all/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Integer customerId) throws OrderException, CustomerException {
        return this.orderService.getOrdersByCustomerId(customerId);
    }

    @PatchMapping("/status/{orderId}")
    public Order updateOrderStatusByOrderId(@PathVariable("orderId") Integer orderId, @RequestBody String orderStatus) throws OrderException {
        return this.orderService.updateOrderStatus(orderId, orderStatus);
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable("status") String status) throws OrderException {
        return this.orderService.getOrdersByStatus(status);
    }

    @PutMapping("/confirm")
    public ResponseEntity<Order> confirmOrder(@RequestBody ConfirmOrderReq confirmOrderReq) throws OrderException {
        return this.orderService.confirmOrder(confirmOrderReq);
    }


}