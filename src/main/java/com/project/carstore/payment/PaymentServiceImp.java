package com.project.carstore.payment;

import com.project.carstore.order.Order;
import com.project.carstore.order.OrderException;
import com.project.carstore.order.OrderRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService{
    private final CardInfoRepository cardInfoRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final Environment env;
    private static final String CURRENCY="currency";
    public PaymentServiceImp(CardInfoRepository cardInfoRepository, PaymentRepository paymentRepository, OrderRepository orderRepository,Environment env)
    {
        this.cardInfoRepository=cardInfoRepository;
        this.paymentRepository=paymentRepository;
        this.orderRepository=orderRepository;
        this.env=env;
    }

    @Override
    public void saveCardInfo(List<CardInfo> cardInfoList) throws PaymentException{
        if(cardInfoList==null)throw new PaymentException("CardInfo cannot be Null");
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

    @Override
    public TransactionDetails createTransaction(Integer orderId) throws PaymentException, OrderException, RazorpayException {
        if(orderId==null || !this.orderRepository.existsById(orderId))throw new PaymentException("Order does not exist with Id:"+orderId);
        // get Order using orderId
        Optional<Order> findOrder=this.orderRepository.findById(orderId);
        //create jsonObject for razorpay
        if(findOrder.isPresent()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("amount", findOrder.get().getTotalPrice() *100);
            jsonObject.put(CURRENCY,env.getProperty(CURRENCY));
            //create razorpay client
            RazorpayClient razorpayClient=new RazorpayClient(env.getProperty("razorpay.api.key"), env.getProperty("razorpay.api.secret"));
            com.razorpay.Order  razorpayOrder =razorpayClient.orders.create(jsonObject);
            String key=env.getProperty("razorpay.api.key");
            String transactionId=razorpayOrder.get("id");
            String currency=razorpayOrder.get(CURRENCY);
            Integer amount=razorpayOrder.get("amount");

            return new TransactionDetails(transactionId,currency,amount.doubleValue(),key);

        }else throw new OrderException("No order Exist with Id:"+orderId);
    }
}
