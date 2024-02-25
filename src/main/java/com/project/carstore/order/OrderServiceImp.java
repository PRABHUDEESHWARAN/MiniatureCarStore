package com.project.carstore.order;


import com.project.carstore.cart.Cart;
import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartItem;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.Address;
import com.project.carstore.customer.AddressRepository;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;

import com.project.carstore.payment.PaymentRepository;
import com.project.carstore.payment.PaymentService;
import com.project.carstore.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service

public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressRepository addressRepository;







    @Override
    public Order createOrder(Integer customerId) throws OrderException {

        //get the customer with customer id
        Optional<Customer> findCustomer = null;
        try {
            findCustomer = this.customerService.getCustomerById(customerId);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
        Optional<Cart> findCart=Optional.empty();
        Order newOrder=new Order(customerId,findCustomer.get().getFirstname(),findCustomer.get().getLastname());
        this.orderRepository.save(newOrder);
        if(findCustomer.isEmpty())
        {
            throw new OrderException("Customer Id cant be null");
        }
        //get the cart with help of cartId with customer
        try {
             findCart=this.cartService.getCartById(findCustomer.get().getCartId());
        } catch (CartException e) {
            System.out.println(e.getMessage());
        }
        //Add the cart items to the order as orderitems by creating orderItem also save the orderitems in orderitems table
        Set<OrderItem> orderItemsToBeAdded=new HashSet<>();
        for(CartItem cartItem:findCart.get().getCartItems())
        {
            OrderItem newOrderItem=new OrderItem(cartItem.getProductId(),cartItem.getQuantity(),cartItem.getTotalPrice(), newOrder.getId());
            orderItemsToBeAdded.add(newOrderItem);
        }
        this.orderItemRepository.saveAll(orderItemsToBeAdded);
        //set remaining values for neworder
        newOrder.setOrderItem(orderItemsToBeAdded);
        newOrder.setTotalItems(orderItemsToBeAdded.size());
        newOrder.setTotalPrice(0.0);
        System.out.println("came till here");
        orderItemsToBeAdded.stream().map(p->p.getTotalPrice()).forEach(p->newOrder.setTotalPrice(newOrder.getTotalPrice()+p));
        newOrder.setOrderStatus("Pending");
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setDeliveryDate(LocalDate.now().plusDays(7));

        //save order to the order repo
        this.orderRepository.save(newOrder);
        //add the order to the customer order list
        try {
            this.customerService.addOrderToCustomer(newOrder);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
        return newOrder;

    }



    @Override
    public Order getOrderById(Integer orderId) throws OrderException {
        if(orderId == 0)
        {
            throw new OrderException("Order should be exist");
        }
        Optional<Order> orderOptional=this.orderRepository.findById(orderId);
        if(orderOptional.isPresent()) {
            return orderOptional.get();
        }
        else
        {
            throw new OrderException("order is not exist");
        }
    }

    @Override
    public Order cancelOrderById(Integer orderId) throws OrderException {
      Optional<Order> orderOpt=this.orderRepository.findById(orderId);

       if(orderOpt.isPresent()) {
           orderOpt.get().setOrderStatus("cancelled");
           this.orderRepository.save(orderOpt.get());
           Optional<Customer> customer=this.customerService.getCustomerById(orderOpt.get().getCustomerId());
           if(customer!=null)
           {
               customer.get().getCustomerOrders().remove(orderOpt.get());
               this.orderRepository.save(orderOpt.get());
           }
       }
       else
       {
           throw new OrderException("No order exist with the orderId to cancel");
       }
       return orderOpt.get();
    }

    @Override
    public  Address addAddressToOrder(Integer orderID, Address newAddress) throws OrderException {
        if(orderID==null)
        {
            throw new OrderException("order should exist before adding address");
        }
        if(newAddress==null)
        {
            throw new OrderException("please provide some address");
        }
        Optional<Order> orderOptional=this.orderRepository.findById(orderID);
        orderOptional.get().setAddress(newAddress);
        return this.addressRepository.save(newAddress);
    }

    @Override
    public Address getAddressByOrderId(Integer orderId) throws OrderException {
        if(orderId==null)
        {
            throw new OrderException("Order not found");
        }
        else
        {
            Optional<Order> orderOptional=this.orderRepository.findById(orderId);
            if(orderOptional.isEmpty())
            {
                throw new OrderException("order not found");
            }
            return orderOptional.get().getAddress();
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate startDate, LocalDate endDate) throws OrderException {
        if(startDate==null||endDate==null)
        {
            throw new OrderException("Dates shouldn't be null");
        }
        return this.orderRepository.findByOrderDateBetween(startDate, endDate);

    }

    @Override
    public Double getTotalPrice(Integer orderId) throws OrderException {
        Optional<Order> orderOptional=this.orderRepository.findById(orderId);

        if(orderOptional.isEmpty())
        {
            throw new OrderException("order not found");
        }
        List<OrderItem> orderItems=new ArrayList<>(orderOptional.get().getOrderItem());
        Double totalAmount=0.0;

        for(int i=0;i<orderItems.size();i++)
        {
            totalAmount+=orderItems.get(i).getTotalPrice();
        }
         orderOptional.get().setTotalPrice(totalAmount);


        return totalAmount;
    }

    @Override
    public Order updatePaymentDetailsByOrderId(Integer orderId, PaymentDetails newPaymentDetails) throws OrderException {
        Optional<Order> order=null;
        if(orderId==null)
        {
            throw new OrderException("order not found");
        }
        this.paymentRepository.save(newPaymentDetails);
        order=this.orderRepository.findById(orderId);
        order.get().setPaymentDetails(newPaymentDetails);
       Order newOrder=order.orElse(null);
       this.orderRepository.save(newOrder);
       return newOrder;

    }

    @Override
    public Order updateDeliveryDateByOrderId(Integer orderId, LocalDate newDeliveryDate) throws OrderException {
        Optional<Order> orderOptional=this.orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
        {
            throw new OrderException("order not found");
        }
        orderOptional.get().setDeliveryDate(newDeliveryDate);
        Order newOrder=orderOptional.orElse(null);
        this.orderRepository.save(newOrder);
        return newOrder;

    }



    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) throws OrderException {


        Optional<Customer> customerOptional=this.customerService.getCustomerById(customerId);
       if(customerOptional.isEmpty())
       {
           throw new OrderException("No customer found with the provided id");
       }
       return customerOptional.get().getCustomerOrders();


    }

    @Override
    public Order updateOrderStatus(Integer orderId, String orderStatus) throws OrderException {
        Optional<Order> orderOptional=this.orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
        {
            throw new OrderException("order not found");
        }
        orderOptional.get().setOrderStatus(orderStatus);
        Order newOrder=orderOptional.orElse(null);
        this.orderRepository.save(newOrder);
        return newOrder;

    }

    @Override
    public List<Order> getOrdersByStatus(String orderStatus) throws OrderException {
        if(orderStatus==null)
        {
            throw new OrderException("orderstatus shouldnt be empty");
        }
        return this.orderRepository.findByOrderStatus(orderStatus);
    }


}
