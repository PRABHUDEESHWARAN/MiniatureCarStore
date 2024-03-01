package com.project.carstore.cart;

import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.exceptions.ProductException;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartServiceImp implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;

    public CartServiceImp(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Cart> getCartById(Integer cartId) throws CartException {
        if (this.cartRepository.existsById(cartId)) {
            return this.cartRepository.findById(cartId);
        } else throw new CartException("No cart exist with Id:" + cartId);
    }

    @Override
    public Integer createCartForCustomer(Integer customerId) {
        return this.cartRepository.save(new Cart(customerId)).getCustomerId();
    }

    @Override
    public Cart addCartItemToCart(CartItemDTO cartItemDTO) throws CartException, ProductException {
        Optional<Cart> cartOptional = this.getCartById(cartItemDTO.getCartId());
        Optional<Product> productOpt = this.productRepository.findById(cartItemDTO.getProductId());
        Optional<CartItem> cartItemOpt = this.cartItemRepository.findCartItemByCartIdAndProductId(cartItemDTO.getCartId(), cartItemDTO.getProductId());
        Cart foundCart;
        Product foundProduct;
        CartItem foundCartItem;
        if (cartOptional.isPresent()) {
            foundCart = cartOptional.get();
            if (productOpt.isPresent()) {
                foundProduct = productOpt.get();
            } else throw new ProductException("Product does not exist with id: " + cartItemDTO.getProductId());
        } else throw new CartException("Cart does not exist");
        Set<CartItem> cartItemSet = foundCart.getCartItems();
        if (cartItemOpt.isPresent()) {
            foundCartItem = cartItemOpt.get();
            Integer quantity = foundCartItem.getQuantity();
            if (foundProduct.getQuantity() > 0) {
                foundCartItem.setQuantity(quantity + 1);
                Double totalPrice = foundCartItem.getTotalPrice();
                foundCartItem.setTotalPrice(totalPrice + foundProduct.getPrice());
                this.cartItemRepository.save(foundCartItem);
                cartItemSet.add(foundCartItem);
            } else throw new CartException("Insufficient Product Stock");
        } else {
            if (foundProduct.getQuantity() > 0) {
                CartItem newCartItem = new CartItem(1, cartItemDTO.getCartId(), cartItemDTO.getProductId(), foundProduct.getPrice());
                this.cartItemRepository.save(newCartItem);
                cartItemSet.add(newCartItem);
            } else throw new CartException("Insufficient Product Stock");
        }
        foundCart.setCartItems(cartItemSet);
        foundCart.setTotalItems(cartItemSet.size());
        AtomicReference<Double> totalPriceOfCart = new AtomicReference<>(0.0);
        cartItemSet.stream().map(CartItem::getTotalPrice).forEach(p -> totalPriceOfCart.updateAndGet(v -> v + p));
        cartOptional.get().setTotalPrice(totalPriceOfCart.get());
        return this.cartRepository.save(cartOptional.get());
    }

    @Override
    public Optional<CartItem> getCartItemByProductIdAndCartId(Long productId, Integer cartId) {
        return this.cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);
    }

    @Override
    public Optional<Cart> getCartByCustomerId(Integer customerId) throws CartException {
        if (!this.customerRepository.existsById(customerId))
            throw new CartException("Invalid customer Id:" + customerId);
        Optional<Customer> customerOpt = this.customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            Integer cartId = customerOpt.get().getCartId();
            Optional<Cart> cartOpt = this.getCartById(cartId);
            if (cartOpt.isPresent()) {
                return cartOpt;
            } else throw new CartException("No cart exist with Id:" + cartId);
        } else throw new CartException("No customer exist with Id:" + customerId);
    }

    @Override
    public String clearCart(Integer customerId) throws CartException {
        if (!this.customerRepository.existsById(customerId))
            throw new CartException("Invalid customer Id:" + customerId);
        Optional<Customer> customerOpt = this.customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            Optional<Cart> cartOpt = this.getCartById(customerOpt.get().getCartId());
            if (cartOpt.isPresent()) {
                Cart foundCart = cartOpt.get();
                //clear cartItems from cartItem Repository
                Set<CartItem> cartItemList = foundCart.getCartItems();
                this.cartItemRepository.deleteAll(cartItemList);
                cartItemList.clear();
                foundCart.setCartItems(cartItemList);
                foundCart.setTotalItems(0);
                foundCart.setTotalPrice(0.0);
                this.cartRepository.save(foundCart);
                return "Cart Cleared Successfully for Customer:" + customerId;
            } else throw new CartException("Cart does not exist for Customer:" + customerId);
        } else throw new CartException("No customer exist with Id:" + customerId);
    }

    @Override
    public Cart checkOut() {
        return null;
    }

    @Override
    public Cart updateCartItem(UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException {
        Optional<Cart> cartOpt = this.getCartById(updateCartItemDTO.getCartId());
        Optional<CartItem> cartItemOpt = this.getCartItemById(updateCartItemDTO.getCartItemId());
        Cart foundCart;
        Product foundProduct;
        CartItem foundCartItem;
        if (cartOpt.isPresent()) {
            foundCart = cartOpt.get();
            if (cartItemOpt.isPresent()) {
                foundCartItem = cartItemOpt.get();
                Optional<Product> productOpt = this.productRepository.findById(foundCartItem.getProductId());
                if (productOpt.isPresent()) {
                    foundProduct = productOpt.get();
                } else throw new ProductException("Product does not exist with Id:" + foundCartItem.getProductId());
            } else
                throw new CartException("CartItem does not exist for cartItemId: " + updateCartItemDTO.getCartItemId());
        } else throw new CartException("Cart does not exist for CartId: " + updateCartItemDTO.getCartId());

        if (updateCartItemDTO.getNewQuantity() != 0 && updateCartItemDTO.getNewQuantity() != null && foundProduct.getQuantity() >= updateCartItemDTO.getNewQuantity()) {
            foundCartItem.setQuantity(updateCartItemDTO.getNewQuantity());
            Double productPrice = foundProduct.getPrice();
            foundCartItem.setTotalPrice(foundCartItem.getQuantity() * productPrice);
            this.cartItemRepository.save(foundCartItem);
            Set<CartItem> cartItemSet = foundCart.getCartItems();
            cartItemSet.add(foundCartItem);
            foundCart.setCartItems(cartItemSet);
            return this.cartRepository.save(foundCart);
        } else throw new CartException("Invalid Quantity or Insufficient Stock:" + updateCartItemDTO.getNewQuantity());
    }

    @Override
    public String removeCartItem(Integer cartItemId) throws CartException {
        Optional<CartItem> cartItemOpt = this.getCartItemById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem foundCartItem = cartItemOpt.get();
            Optional<Cart> cartOpt = this.getCartById(foundCartItem.getCartId());
            if (cartOpt.isPresent()) {
                Cart foundCart = cartOpt.get();
                foundCart.getCartItems().remove(foundCartItem);
                this.cartRepository.save(foundCart);
                this.cartItemRepository.delete(foundCartItem);
                return "cart Item Removed Successfully";
            } else throw new CartException("Cart does not exist for cartId: " + foundCartItem.getCartId());
        } else throw new CartException("CartItem does not exist for cartItemId: " + cartItemId);
    }

    @Override
    public Set<CartItem> getAllCartItems(Integer cartId) throws CartException {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            return cartOpt.get().getCartItems();
        } else throw new CartException("Cart id does not exist");

    }

    @Override
    public Optional<CartItem> getCartItemById(Integer cartItemId) {
        return this.cartItemRepository.findById(cartItemId);
    }

    @Override
    public Double getCartTotal(Integer cartId) throws CartException {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            return cartOpt.get().getTotalPrice();
        } else throw new CartException("No cart present for the cart Id");
    }
}