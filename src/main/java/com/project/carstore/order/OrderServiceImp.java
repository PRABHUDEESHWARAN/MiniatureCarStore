package com.project.carstore.order;

import com.project.carstore.cart.Cart;
import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartItem;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.payment.PaymentDetails;

import com.project.carstore.payment.PaymentException;
import com.project.carstore.payment.PaymentRepository;
import com.project.carstore.payment.PaymentService;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductRepository;
import com.project.carstore.product.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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





    @Override
    public Order createOrder(Integer customerId) throws OrderException {

        //get the customer with customer id
        Optional<Customer> findCustomer = this.customerService.getCustomerById(customerId);
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
        //save order to the order repo
        this.orderRepository.save(newOrder);
        //add the order to the customer order list
        this.customerService.addOrderToCustomer(newOrder);
        return newOrder;

    }

    @Override
    public List<Order> addOrderToCustomerOrdersList(Order newOrder) throws OrderException {
        if(newOrder.getId()==null)
        {
            throw new OrderException("order need to be created");
        }
        Optional<Customer> customerOptional=this.customerService.getCustomerById(newOrder.getCustomerId());
       if(customerOptional.isPresent())
       {
           customerOptional.get().getCustomerOrders().add(newOrder);
       }

      return customerOptional.get().getCustomerOrders();
    }

    @Override
    public Order getOrderById(Integer id) throws OrderException {
        if(id == 0)
        {
            throw new OrderException("Order should be exist");
        }
        return this.orderRepository.findById(id).get();
    }

    @Override
    public Order deleteOrderById(Integer id) {
       Optional<Order> orderOpt=this.orderRepository.findById(id);
       this.orderRepository.deleteById(id);
       return orderOpt.get();
    }

    @Override
    public Order updateOrder(Order newOrder) {
       return this.orderRepository.save(newOrder);

    }


    @Override
    public Double getTotalPrices(List<OrderItem> orderItems) throws OrderException {
        if(orderItems.isEmpty())
        {
            throw  new OrderException("no item exist");
        }
        Double totalPrice=0.0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public LocalDate getOrderDate() {
        return LocalDate.now();
    }

    @Override
    public Boolean paymentStatus(PaymentDetails paymentDetails) throws OrderException {
        if(paymentDetails.getOrderId()==null)
        {
            throw new OrderException("order is not exist to check the status");
        }
        return paymentDetails.getStatus();
    }

    @Override
    public String getOrderStatusById(Integer id) throws OrderException {
        if(this.getOrderById(id)==null)
        {
            throw new OrderException("order is not exist");
        }

         return this.getOrderById(id).getOrderStatus();
    }

    @Override
    public LocalDate getDeliveryDateByOrderId(Order order) throws OrderException {
        if(order==null)
        {
            throw new OrderException("order should be exist");
        }
        LocalDate orderDate=order.getOrderDate();
        return orderDate.plusDays(7);
    }

    @Override
    public Integer getTotalNumberOfItems(List<OrderItem> orderItems) throws OrderException {
        if(orderItems.isEmpty())
        {
            throw new OrderException("no item exists");
        }
        return orderItems.size();
    }

    @Override
    public Boolean checkAvailabilityOfOrderItem(Order order) {
        if(order.getId()==null)
        {
            return false;
        }
        return true;
    }


}
