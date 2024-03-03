package com.project.carstore.payment;

import com.project.carstore.order.OrderException;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService)
    {
        this.paymentService=paymentService;
    }

    @GetMapping("/transaction/{orderId}")
    public TransactionDetails createTransaction(@PathVariable("orderId") Integer orderId) throws PaymentException, OrderException, RazorpayException {
        return this.paymentService.createTransaction(orderId);
    }

}
