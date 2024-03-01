package com.project.carstore.cart;

import com.project.carstore.exceptions.ProductException;

import java.util.Optional;
import java.util.Set;

public interface CartService {
    Optional<Cart> getCartById(Integer cartId) throws CartException;

    Integer createCartForCustomer(Integer id) throws CartException;

    Cart addCartItemToCart(CartItemDTO cartItemDTO) throws CartException, ProductException;

    Optional<CartItem> getCartItemByProductIdAndCartId(Long productId,Integer cartId);

    Optional<Cart> getCartByCustomerId(Integer customerId) throws CartException;

    String clearCart(Integer customerId) throws CartException;

    Cart checkOut() throws CartException;

    Cart updateCartItem(UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException;

    String removeCartItem(Integer cartItemId) throws CartException;

    Set<CartItem> getAllCartItems(Integer cartId) throws CartException;

    Optional<CartItem> getCartItemById(Integer cartItemId) throws CartException;

    Double getCartTotal(Integer cartId) throws CartException;
}