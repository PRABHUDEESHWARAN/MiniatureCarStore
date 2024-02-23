package com.project.carstore.payment;

import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    public void SaveCardInfo(List<CardInfo> cardInfoList);

    public Optional<PaymentDetails> getPaymentDetailsById(Integer paymentId) throws PaymentException;
}
