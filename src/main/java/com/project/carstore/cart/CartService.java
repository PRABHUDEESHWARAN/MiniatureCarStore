package com.project.carstore.cart;

import java.util.Optional;

public interface CartService {
    public Optional<Cart> getCartById(Integer cartId) throws CartException;

    Integer CreateCartForCustomer(Integer id);
    public Cart addCartItemToCart(CartItemDTO cartItemDTO);
    public Optional<CartItem> getCartItemByProductId(Long ProductId);
}
