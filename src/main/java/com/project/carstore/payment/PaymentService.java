package com.project.carstore.payment;

import com.project.carstore.order.OrderException;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    public void SaveCardInfo(List<CardInfo> cardInfoList) throws PaymentException;

    public Optional<PaymentDetails> getPaymentDetailsById(Integer paymentId) throws PaymentException;
    public TransactionDetails createTransaction(Integer orderId) throws PaymentException, OrderException, RazorpayException;
}
