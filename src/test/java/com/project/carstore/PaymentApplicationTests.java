package com.project.carstore;

import com.project.carstore.order.Order;
import com.project.carstore.order.OrderException;
import com.project.carstore.order.OrderRepository;
import com.project.carstore.payment.*;
import com.razorpay.RazorpayException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class PaymentApplicationTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName(value = "Create Transaction Test")
    void createTransactionTest() throws PaymentException, OrderException, RazorpayException {
        Order sampleOrder = new Order(1, "vishnu", "priya");
        sampleOrder.setTotalPrice(1000.0);
        this.orderRepository.save(sampleOrder);
        TransactionDetails transactionDetails = this.paymentService.createTransaction(sampleOrder.getId());
        Assertions.assertNotNull(transactionDetails);
    }

    @Test
    @DisplayName(value = "CreateTransaction Valid OrderId Test")
    void createTransactionValidIdTest() {
        try {
            TransactionDetails transactionDetails = this.paymentService.createTransaction(null);
        } catch (PaymentException | OrderException | RazorpayException e) {
            Assertions.assertEquals("Order does not exist with Id:" + null, e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "Save CardInfo Test")
    void saveCardInfoInvalidDataTest() {
        try {
            this.paymentService.saveCardInfo(null);
        } catch (PaymentException e) {
            Assertions.assertEquals("CardInfo cannot be Null", e.getMessage());
        }

    }

    @Test
    @DisplayName(value = "get Payment Details By Id")
    void getPaymentDetailsByIdTest() {
        PaymentDetails paymentDetails = new PaymentDetails();
        this.paymentRepository.save(paymentDetails);
        try {
            Optional<PaymentDetails> findPaymentDetails = this.paymentService.getPaymentDetailsById(paymentDetails.getPaymentId());
            Assertions.assertNotNull(findPaymentDetails.get());
        } catch (PaymentException e) {
            Assertions.assertEquals("No payment details exists with such id:" + paymentDetails.getPaymentId(), e.getMessage());
        }
    }

}
