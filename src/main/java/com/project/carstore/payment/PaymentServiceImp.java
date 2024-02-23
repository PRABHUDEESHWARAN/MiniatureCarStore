package com.project.carstore.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService{
    @Autowired
    private CardInfoRepository cardInfoRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public void SaveCardInfo(List<CardInfo> cardInfoList) {
        this.cardInfoRepository.saveAll(cardInfoList);
    }

    @Override
    public Optional<PaymentDetails> getPaymentDetailsById(Integer paymentId) throws PaymentException {
        if(this.paymentRepository.existsById(paymentId))
        {
            return this.paymentRepository.findById(paymentId);
        }
        else throw new PaymentException("No payment details exists with such id:"+paymentId);
    }
}
