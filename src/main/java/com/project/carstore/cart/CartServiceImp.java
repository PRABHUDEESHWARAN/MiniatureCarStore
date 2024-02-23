package com.project.carstore.cart;

import com.project.carstore.exceptions.CartExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;



    @Override
    public Cart removeItemFromCart(Integer cartId, Integer cartItemId) {
        return null;
    }

    @Override
    public Cart updateItemQuantity(Integer cartItemId, Integer Quantity) {
        return null;
    }

    @Override
    public Cart clearCartItems(Integer cartId) {
        return null;
    }

    @Override
    public Cart viewCartItems() {
        return null;
    }

    @Override
    public BigDecimal calculateTotalCartValue(Integer cartId) {
        return null;
    }

    @Override
    public boolean checkoutCart(Integer cartId) {
        return false;
    }
    private Cart updateCart(Cart cart){
        Integer totalPrice = 0;
        Integer totalItems = 0;
        for (CartItem item : cart.getCartItems()){
            totalPrice += item.getPrice()* item.getQuantity();
            totalItems += item.getQuantity();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        return this.cartRepository.save(cart);



    }

}