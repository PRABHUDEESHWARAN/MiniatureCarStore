package com.project.carstore;


import com.project.carstore.cart.*;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.exceptions.ProductException;
import com.project.carstore.product.Product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CartServiceTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CustomerRepository customerRepository;


    //updateCartItem tests
    @Test
    @DisplayName(value = "updating null product")
    public void updateCartItemTest_NullCartItem() throws CartException, ProductException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            cartService.updateCartItem(null);
        });
    }

    // addProductToCart test
    @Test
    @DisplayName(value = "adding null product to cart")
    public void addProductToCartTest_NullCartItem() throws CartException, ProductException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            cartService.addCartItemToCart(null);
        });
    }
//    @Test
//    @DisplayName(value = "adding product")
//    public void addProductToCartTest_validProduct() throws CartException {
//        Cart cart = new Cart();
//        cart = this.cartService.addCartItemToCart(cart);
//        Assertions.assertNotNull(cart);
//        }

    // getCartItemByProductId tests
    @Test
    public void testGetCartItemByProduct_IdNotExists() {
        Long productId = 456L;

        Optional<CartItem> actualCartItem = cartItemRepository.findCartItemByProductId(productId);

        assertFalse(actualCartItem.isPresent());
    }

    //clear cart tests
    @Test
    public void testClearCart_CustomerNotFound() {
        Integer customerId = 789;

        assertThrows(CartException.class, () -> cartService.clearCart(customerId));
    }

    @Test
    public void testClearCart_CartNotFound() {
        Integer customerId = 123;

        assertThrows(CartException.class, () -> cartService.clearCart(customerId));
    }

    // get cart total tests

    @Test
    void testGetCartTotal_CartNotPresent() {
        Integer invalidCartId = -1;

        assertThrows(CartException.class, () -> cartService.getCartTotal(invalidCartId));
    }

    // create cart tests
    @Test
    @DisplayName("Create Cart For Customer - Valid Customer Id")
    public void testCreateCartForValidCustomer() throws CartException {
        Integer customerId = 123; // Example customer ID
        Integer cartId = cartService.CreateCartForCustomer(customerId);

        assertNotNull(cartId);
        assertTrue(cartId > 0);
    }

    //get card by id tests
    @Test
    @DisplayName("Get Cart By Id - Valid Cart Id")
    public void testGetCartByIdValidCartId() throws CartException {
        Integer cartId = 1; // Example cart ID
        Cart cart = cartService.getCartById(cartId).orElseThrow(() -> new CartException("No cart found"));

        assertNotNull(cart);
        assertEquals(cartId, cart.getId());
    }

    @Test
    @DisplayName("Get Cart By Id - Invalid Cart Id")
    public void testGetCartByIdInvalidCartId() {
        Integer cartId = 999; // Example invalid cart ID
        CartException exception = assertThrows(CartException.class, () -> {
            cartService.getCartById(cartId);
        });

        assertNotNull(exception);
        assertEquals("No cart exist with Id:999", exception.getMessage());
    }


//get all cart items tests
    @Test
    @DisplayName("Get All Cart Items - Invalid Cart Id")
    public void testGetAllCartItemsInvalidCartId() {
        Integer cartId = -1; // Example invalid cart ID
        CartException exception = assertThrows(CartException.class, () -> {
            cartService.getAllCartItems(cartId);
        });

        assertNotNull(exception);
        assertEquals("Cart id does not exist", exception.getMessage());
    }


// remove cart item from cart tests
    @Test
    @DisplayName("Remove Cart Item - Invalid Cart Item Id")
    public void testRemoveCartItemInvalidCartItemId() {
        Integer cartItemId = 999; // Example invalid cart item ID
        CartException exception = assertThrows(CartException.class, () -> {
            cartService.removeCartItem(cartItemId);
        });

        assertNotNull(exception);
        assertEquals("CartItem does not exist for cartItemId: " + cartItemId, exception.getMessage());
    }
// getCartItemById tests
    @Test
    @DisplayName("Get Cart Item By Id - Invalid Cart Item Id")
    public void testGetCartItemByIdInvalidId() throws CartException {
        Integer cartItemId = -1; // Example invalid cart item ID

        Optional<CartItem> result = cartService.getCartItemById(cartItemId);

        assertTrue(result.isEmpty());
    }

// getCartByCustomerId
    @Test
    @DisplayName("Get Cart By Customer Id - Invalid Customer Id")
    public void testGetCartByCustomerIdInvalidId() {
        Integer customerId = 0; // Example invalid customer ID

        CartException exception = assertThrows(CartException.class, () -> {
            cartService.getCartByCustomerId(customerId);
        });

        // Assert the exception message
        assertEquals("No customer exist with Id:" + customerId, exception.getMessage());
    }


}


















