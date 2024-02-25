package com.project.carstore.cart;

import com.project.carstore.exceptions.ProductException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CartService {
    public Optional<Cart> getCartById(Integer cartId) throws CartException;

    Integer CreateCartForCustomer(Integer id) throws CartException;
    public Cart addCartItemToCart(CartItemDTO cartItemDTO) throws CartException;
    public Optional<CartItem> getCartItemByProductId(Long ProductId) throws CartException;
    public  Optional<Cart> getCartByCustomerId(Integer customerId) throws CartException ;
    public String clearCart(Integer customerId) throws CartException;
    public Cart checkOut() throws CartException;
    public Cart updateCartItem(UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException;
    public String removeCartItem(Integer cartItemId) throws CartException;
    Set<CartItem> getAllCartItems(Integer cartId)throws CartException;
    Optional<CartItem> getCartItemById(Integer cartItemId) throws CartException;
    public Double getCartTotal(Integer cartId) throws CartException;
}