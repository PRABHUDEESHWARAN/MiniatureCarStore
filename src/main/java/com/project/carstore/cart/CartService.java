package com.project.carstore.cart;
import java.math.BigDecimal;
import java.util.List;


public interface CartService {
    public  Cart addItemtoCart(Integer cartId, Integer productId , Integer Quantity);
    public Cart removeItemFromCart(Integer cartId, Integer cartItemId);
    public Cart updateItemQuantity(Integer cartItemId, Integer Quantity);
    public Cart clearCartItems(Integer cartId);
    List<CartItem> viewCartItems(Integer cartId);
    BigDecimal calculateTotalCartValue(Integer cartId);
    boolean checkoutCart(Integer cartId);

}
