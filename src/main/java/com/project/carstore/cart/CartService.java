package com.project.carstore.cart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    public Optional<Cart> getCartById(Integer cartId) throws CartException;

    Integer CreateCartForCustomer(Integer id);
    public Cart addCartItemToCart(CartItemDTO cartItemDTO);
    public Optional<CartItem> getCartItemByProductId(Long ProductId);
    public  Cart getCustomerCart(Integer customerId) throws CartException;
    public Cart clearCart(Integer cartId) throws CartException;
    public Cart checkOut();
    public Cart updateCartItem(Integer cartItemId, CartItem updatedCartItem);
    public Cart removeCartItem(Integer cartItemId) throws CartException;
    List<CartItem> getAllCartItems (Integer cartId)throws CartException;
    CartItem getCartItemById(Integer cartItemId);
}