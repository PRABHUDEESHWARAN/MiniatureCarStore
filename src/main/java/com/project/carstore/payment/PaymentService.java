package com.project.carstore.payment;

import com.project.carstore.order.OrderException;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    void saveCardInfo(List<CardInfo> cardInfoList) throws PaymentException;

    Optional<PaymentDetails> getPaymentDetailsById(Integer paymentId) throws PaymentException;
    TransactionDetails createTransaction(Integer orderId) throws PaymentException, OrderException, RazorpayException;
}
