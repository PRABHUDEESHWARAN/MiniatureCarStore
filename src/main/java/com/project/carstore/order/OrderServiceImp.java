package com.project.carstore.order;


import com.project.carstore.cart.Cart;
import com.project.carstore.cart.CartException;
import com.project.carstore.cart.CartItem;
import com.project.carstore.cart.CartService;
import com.project.carstore.customer.*;
import com.project.carstore.exceptions.CustomerException;
import com.project.carstore.payment.PaymentDetails;
import com.project.carstore.payment.PaymentRepository;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final CartService cartService;
    private final CustomerRepository customerRepository;

    private static final String ISSUE = "Order does not exist with Id:";

    public OrderServiceImp(CustomerRepository customerRepository, OrderRepository orderRepository, CustomerService customerService, ProductRepository productRepository, OrderItemRepository orderItemRepository, PaymentRepository paymentRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<StockValidationResponse> createOrder(Integer customerId) throws OrderException, CustomerException, CartException {
        List<String> insufficientProducts = new ArrayList<>();
        List<String> outOfStockProducts = new ArrayList<>();
        List<String> stockIssues = new ArrayList<>();

        Customer foundCustomer;
        Cart foundCart;

        Optional<Customer> customerOpt = this.customerService.getCustomerById(customerId);
        if (customerOpt.isPresent()) {
            foundCustomer = customerOpt.get();
        } else throw new CustomerException("Customer does not exist with Id:" + customerId);

        Optional<Cart> cartOpt = this.cartService.getCartById(foundCustomer.getCartId());
        if (cartOpt.isPresent()) {
            foundCart = cartOpt.get();
        } else throw new CartException("Cart does not exist with id:" + foundCustomer.getCartId());

        if (foundCart.getCartItems().isEmpty())
            throw new CartException("Cart is Empty!!! Please add cartItems");

        Order newOrder = new Order(customerId, foundCustomer.getFirstname(), foundCustomer.getLastname());
        this.orderRepository.save(newOrder);
        Set<OrderItem> orderItemsToBeAdded = new HashSet<>();

        for (CartItem cartItem : foundCart.getCartItems()) {
            Integer checkQuantity = cartItem.getQuantity();
            Product foundProduct;
            Optional<Product> productOpt = this.productRepository.findById(cartItem.getProductId());
            if (productOpt.isPresent()) {
                foundProduct = productOpt.get();
                if (checkQuantity > foundProduct.getQuantity() && foundProduct.getQuantity() > 0) {
                    checkQuantity = foundProduct.getQuantity();
                    insufficientProducts.add("Insufficient Stock: Quantity set to Available units PId:" + foundProduct.getId() + "\n");
                    OrderItem newOrderItem = new OrderItem(cartItem.getProductId(), checkQuantity, cartItem.getTotalPrice(), newOrder.getId());
                    orderItemsToBeAdded.add(newOrderItem);
                } else if (checkQuantity < foundProduct.getQuantity()) {
                    OrderItem newOrderItem = new OrderItem(cartItem.getProductId(), checkQuantity, cartItem.getTotalPrice(), newOrder.getId());
                    orderItemsToBeAdded.add(newOrderItem);
                } else {
                    outOfStockProducts.add("Product:" + foundProduct.getName() + " Out Of Stock => Removed from Order.\n");
                }
            } else throw new OrderException("Product does not exist with id:" + cartItem.getProductId());
        }

        this.orderItemRepository.saveAll(orderItemsToBeAdded);
        newOrder.setOrderItem(orderItemsToBeAdded);
        newOrder.setTotalItems(orderItemsToBeAdded.size());
        newOrder.setTotalPrice(0.0);
        orderItemsToBeAdded.stream().map(OrderItem::getTotalPrice).forEach(p -> newOrder.setTotalPrice(newOrder.getTotalPrice() + p));
        newOrder.setOrderStatus("Pending");
        this.orderRepository.save(newOrder);
        this.customerService.addOrderToCustomer(newOrder);
        stockIssues.addAll(insufficientProducts);
        stockIssues.addAll(outOfStockProducts);
        this.cartService.clearCart(customerId);
        return new ResponseEntity<>(new StockValidationResponse(newOrder, stockIssues), HttpStatus.ACCEPTED);
    }

    @Override
    public Optional<Order> getOrderById(Integer orderId) throws OrderException {
        if (orderId == null || orderId == 0) {
            throw new OrderException("Invalid OrderId:" + orderId);
        }
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional;
        } else {
            throw new OrderException("order does not exist for Id:" + orderId);
        }
    }

    @Override
    public Order closeOrderById(Integer orderId) throws OrderException, CustomerException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);
        Order foundOrder;
        Customer foundCustomer;
        if (orderOpt.isPresent()) {
            foundOrder = orderOpt.get();
            Optional<Customer> customerOpt = this.customerService.getCustomerById(orderOpt.get().getCustomerId());
            if (customerOpt.isPresent()) {
                foundCustomer = customerOpt.get();
                foundOrder.setOrderStatus("closed");
                this.orderRepository.save(foundOrder);
                List<Order> customerOrdersList = foundCustomer.getCustomerOrders();
                customerOrdersList.remove(foundOrder);
                foundCustomer.setCustomerOrders(customerOrdersList);
                this.customerRepository.save(foundCustomer);
                return foundOrder;
            } else throw new CustomerException("Customer does not exist with Id:" + foundOrder.getCustomerId());
        } else {
            throw new OrderException("No order exist with the orderId to cancel");
        }
    }

    @Override
    public ResponseEntity<Order> addAddressToOrder(Integer orderId, AddressDto newAddress) throws OrderException, CustomerException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);
        Order foundOrder;
        if (orderOpt.isPresent() && Objects.equals(orderOpt.get().getCustomerId(), newAddress.getCustomerId())) {
            foundOrder = orderOpt.get();
            this.customerService.addAddressToCustomer(newAddress);
            foundOrder.setAddress(new Address(newAddress.getCustomerId(), newAddress.getDoorNo(), newAddress.getCity(), newAddress.getPincode(), newAddress.getState()));
            this.orderRepository.save(foundOrder);
        } else throw new OrderException(ISSUE + orderId);
        return new ResponseEntity<>(orderOpt.get(), HttpStatus.ACCEPTED);
    }

    @Override
    public Address getAddressByOrderId(Integer orderId) throws OrderException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            return orderOpt.get().getAddress();
        } else throw new OrderException(ISSUE + orderId);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate startDate, LocalDate endDate) throws OrderException {
        if (startDate == null || endDate == null) {
            throw new OrderException("Dates shouldn't be null");
        }
        return this.orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public Double getTotalPrice(Integer orderId) throws OrderException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new OrderException(ISSUE + orderId);
        }
        return orderOpt.get().getTotalPrice();
    }

    @Override
    public Order updatePaymentDetailsByOrderId(Integer orderId, PaymentDetails newPaymentDetails) throws OrderException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);
        Order foundOrder;
        this.paymentRepository.save(newPaymentDetails);
        if (orderOpt.isPresent()) {
            foundOrder = orderOpt.get();
            foundOrder.setPaymentDetails(newPaymentDetails);
            this.orderRepository.save(foundOrder);
            return foundOrder;
        } else throw new OrderException(ISSUE + orderId);
    }

    @Override
    public Order updateDeliveryDateByOrderId(Integer orderId, LocalDate newDeliveryDate) throws OrderException {
        Optional<Order> orderOpt = this.orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new OrderException(ISSUE + orderId);
        }
        orderOpt.get().setDeliveryDate(newDeliveryDate);
        return this.orderRepository.save(orderOpt.get());
    }

    @Override
    public ResponseEntity<Order> confirmOrder(@NotNull ConfirmOrderReq confirmOrderReq) throws OrderException {
        Optional<Order> orderOpt = this.orderRepository.findById(confirmOrderReq.getOrderId());
        Order foundOrder;
        if (orderOpt.isPresent()) {
            foundOrder = orderOpt.get();
            foundOrder.setTransactionId(confirmOrderReq.getTransactionId());
            foundOrder.setOrderStatus("Paid");
            foundOrder.setOrderDate(LocalDate.now());
            LocalDate orderDate = foundOrder.getOrderDate();
            orderOpt.get().setDeliveryDate(orderDate.plusDays(3));
            this.orderRepository.save(foundOrder);
            return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        } else throw new OrderException(ISSUE + confirmOrderReq.getOrderId());
    }

    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) throws OrderException, CustomerException {
        Optional<Customer> customerOpt = this.customerService.getCustomerById(customerId);
        if (customerOpt.isEmpty()) {
            throw new OrderException("No customer found with the provided id:" + customerId);
        }
        return customerOpt.get().getCustomerOrders();
    }

    @Override
    public Order updateOrderStatus(Integer orderId, String orderStatus) throws OrderException {
        Optional<Order> orderOpt = Optional.empty();
        if (orderId != null) {
            orderOpt = this.orderRepository.findById(orderId);
        }
        if (orderOpt.isEmpty()) {
            throw new OrderException(ISSUE + orderId);
        }
        orderOpt.get().setOrderStatus(orderStatus);
        return this.orderRepository.save(orderOpt.get());
    }

    @Override
    public List<Order> getOrdersByStatus(String orderStatus) throws OrderException {
        if (orderStatus == null) {
            throw new OrderException("order status cannot be empty");
        }
        return this.orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }
}
