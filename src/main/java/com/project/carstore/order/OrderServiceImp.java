package com.project.carstore.order;


import com.project.carstore.cart.Cart;
import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartItem;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.*;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;

import com.project.carstore.payment.PaymentRepository;
import com.project.carstore.payment.PaymentService;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private CustomerRepository customerRepository;
    public OrderServiceImp(CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }







    @Override
    public ResponseEntity<StockValidationResponse> createOrder(Integer customerId) throws OrderException, CustomerException, CartException {

        //get the customer with customer id
        Optional<Customer> findCustomer = Optional.empty();
        List<String> insufficientProducts = new ArrayList<>();
        List<String> outOfStockProducts = new ArrayList<>();
        List<String> stockIssues=new ArrayList<>();
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
        if(findCart.isPresent() && findCart.get().getCartItems().isEmpty()) throw new OrderException("Cart is Empty!!! Please add cartItems");
        Set<CartItem> cartItemsSet=findCart.get().getCartItems();
        for(CartItem cartItem:findCart.get().getCartItems())
        {
            Optional<Product> findProduct=this.productRepository.findById(cartItem.getProductId());
            Integer checkQuantity= cartItem.getQuantity();
            if(checkQuantity>findProduct.get().getQuantity() && findProduct.get().getQuantity()>0){
                checkQuantity=findProduct.get().getQuantity();
                insufficientProducts.add("Insufficient Stock: Quantity set to Available units PId:"+ findProduct.get().getId() +"\n");
                OrderItem newOrderItem=new OrderItem(cartItem.getProductId(),checkQuantity,cartItem.getTotalPrice(), newOrder.getId());
                orderItemsToBeAdded.add(newOrderItem);
            }else{
                outOfStockProducts.add("Product:"+findProduct.get().getName()+" Out Of Stock => Removed from Order.\n");
            }
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
        stockIssues.addAll(insufficientProducts);
        stockIssues.addAll(outOfStockProducts);
        this.cartService.clearCart(customerId);
        return  new ResponseEntity<StockValidationResponse>(new StockValidationResponse(newOrder,stockIssues),HttpStatus.ACCEPTED);
    }



    @Override
    public Order getOrderById(Integer orderId) throws OrderException {
        if(orderId ==null || orderId==0)
        {
            throw new OrderException("Invalid OrderId:"+orderId);
        }
        Optional<Order> orderOptional=this.orderRepository.findById(orderId);
        if(orderOptional.isPresent()) {
            return orderOptional.get();
        }
        else
        {
            throw new OrderException("order does not exist for Id:"+orderId);
        }
    }

    @Override
    public Order closeOrderById(Integer orderId) throws OrderException, CustomerException {
      Optional<Order> findOrder=this.orderRepository.findById(orderId);
       if(findOrder.isPresent()) {
           findOrder.get().setOrderStatus("closed");
           this.orderRepository.save(findOrder.get());
           Optional<Customer> findCustomer=this.customerService.getCustomerById(findOrder.get().getCustomerId());
           if(findCustomer.isPresent())
           {
               List<Order> customerOrder =findCustomer.get().getCustomerOrders();
               customerOrder.remove(findOrder.get());
               findCustomer.get().setCustomerOrders(customerOrder);
               this.customerRepository.save(findCustomer.get());
               return findOrder.get();
           }else throw new CustomerException("Customer does not exist with Id:"+findOrder.get().getCustomerId());
       }
       else
       {
           throw new OrderException("No order exist with the orderId to cancel");
       }
    }

    @Override
    public  ResponseEntity<Order> addAddressToOrder(Integer orderId,AddressDto newAddress) throws OrderException, CustomerException {
        Optional<Order>findOrder=this.orderRepository.findById(orderId);
        if(findOrder.isPresent() && findOrder.get().getCustomerId() == newAddress.getCustomerId())
        {
            this.customerService.addAddressToCustomer(newAddress);
            findOrder.get().setAddress(new Address(newAddress.getCustomerId(), newAddress.getDoorNo(), newAddress.getCity(), newAddress.getPincode(), newAddress.getState()));
            this.orderRepository.save(findOrder.get());
        }else throw new OrderException("Order does not exist with Id:"+orderId);
        return new ResponseEntity<Order>(findOrder.get(),HttpStatus.ACCEPTED);
    }

    @Override
    public Address getAddressByOrderId(Integer orderId) throws OrderException {
        Optional<Order> findOrder=this.orderRepository.findById(orderId);
        if(findOrder.isPresent())
        {
            return findOrder.get().getAddress();
        }else throw new OrderException("Order does not exist with Id:"+orderId);
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
        Optional<Order> findOrder=this.orderRepository.findById(orderId);

        if(findOrder.isEmpty())
        {
            throw new OrderException("Order does not exist with Id:"+orderId);
        }
        return findOrder.get().getTotalPrice();
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
        Optional<Order> findOrder=this.orderRepository.findById(orderId);
        if(findOrder.isEmpty())
        {
            throw new OrderException("order does not exist with Id:"+orderId);
        }
        findOrder.get().setDeliveryDate(newDeliveryDate);
        return this.orderRepository.save(findOrder.get());
    }
    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) throws OrderException, CustomerException {
        Optional<Customer> findCustomer=this.customerService.getCustomerById(customerId);
       if(findCustomer.isEmpty())
       {
           throw new OrderException("No customer found with the provided id:"+customerId);
       }
       return findCustomer.get().getCustomerOrders();


    }

    @Override
    public Order updateOrderStatus(Integer orderId, String orderStatus) throws OrderException {
        Optional<Order> findOrder=null;
        if(orderId!=null){
            findOrder=this.orderRepository.findById(orderId);
        }
        if(findOrder.isEmpty())
        {
            throw new OrderException("order does not exist with Id:"+orderId);
        }
        findOrder.get().setOrderStatus(orderStatus);
        return this.orderRepository.save(findOrder.get());
    }

    @Override
    public List<Order> getOrdersByStatus(String orderStatus) throws OrderException {
        if(orderStatus==null)
        {
            throw new OrderException("order status cannot be empty");
        }
        return this.orderRepository.findByOrderStatus(orderStatus);
    }


}
