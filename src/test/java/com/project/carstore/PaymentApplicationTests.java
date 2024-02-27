package com.project.carstore;

import com.project.carstore.order.Order;
import com.project.carstore.order.OrderException;
import com.project.carstore.order.OrderRepository;
import com.project.carstore.payment.*;
import com.razorpay.RazorpayException;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentApplicationTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentService paymentService;

    @Test
    @DisplayName(value = "Create Transaction Test")
    void createTransactionTest() throws PaymentException, OrderException, RazorpayException{
        Order sampleOrder = new Order(1,"vishnu","priya");
        sampleOrder.setTotalPrice(1000.0);
        this.orderRepository.save(sampleOrder);
        TransactionDetails transactionDetails=this.paymentService.createTransaction(sampleOrder.getId());
        Assertions.assertNotNull(transactionDetails);
    }
    @Test
    @DisplayName(value="CreateTransaction Valid OrderId Test")
    void createTransactionValidIdTest(){
        try{
            TransactionDetails transactionDetails=this.paymentService.createTransaction(null);
        }catch (PaymentException | OrderException | RazorpayException e)
        {
            Assertions.assertEquals("Order does not exist with Id:"+null,e.getMessage());
        }
    }
    @Test
    @DisplayName(value="Save CardInfo Test")
    void saveCardInfoInvalidDataTest() {
        try{
            this.paymentService.SaveCardInfo(null);
        }catch (PaymentException e)
        {
            Assertions.assertEquals("CardInfo cannot be Null",e.getMessage());
        }

    }
}
