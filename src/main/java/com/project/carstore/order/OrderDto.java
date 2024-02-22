package com.project.carstore.order;

import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.payment.PaymentDetails;

import java.util.List;

public class OrderDto {
    private CustomerDTO customerDTO;
    private List<OrderItem> orderItems;
    private PaymentDetails paymentDetails;

    public OrderDto(CustomerDTO customerDTO, List<OrderItem> orderItems, PaymentDetails paymentDetails) {
        this.customerDTO = customerDTO;
        this.orderItems = orderItems;
        this.paymentDetails = paymentDetails;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
