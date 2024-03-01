package com.project.carstore;
import com.project.carstore.cart.*;
import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.exceptions.ProductException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartServicesTest {
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
    void updateCartItemTest_NullCartItem() throws CartException, ProductException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            cartService.updateCartItem(null);
        });
    }

    // addProductToCart test
    @Test
    @DisplayName(value = "adding null product to cart")
    void addProductToCartTest_NullCartItem() throws CartException, ProductException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            cartService.addCartItemToCart(null);
        });
    }
    @Test
    void testGetCartItemByProduct_IdNotExists() {
        Long productId = 456L;

        Optional<CartItem> actualCartItem = cartItemRepository.findCartItemByProductId(productId);

        assertFalse(actualCartItem.isPresent());
    }

    //clear cart tests
    @Test
    void testClearCart_CustomerNotFound() {
        Integer customerId = 789;

        assertThrows(CartException.class, () -> cartService.clearCart(customerId));
    }

    @Test
    void testClearCart_CartNotFound() {
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
    void testCreateCartForValidCustomer() throws CartException {
        Integer customerId = 123; // Example customer ID
        Integer cartId = cartService.createCartForCustomer(customerId);

        assertNotNull(cartId);
        assertTrue(cartId > 0);
    }

    //get card by id tests
    @Test
    @DisplayName("Get Cart By Id - Valid Cart Id")
    void testGetCartByIdValidCartId() throws CartException {
        Integer cartId = 1; // Example cart ID
        Cart cart = cartService.getCartById(cartId).orElseThrow(() -> new CartException("No cart found"));

        assertNotNull(cart);
        assertEquals(cartId, cart.getId());
    }

    @Test
    @DisplayName("Get Cart By Id - Invalid Cart Id")
    void testGetCartByIdInvalidCartId() {
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
    void testGetAllCartItemsInvalidCartId() {
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
    void testRemoveCartItemInvalidCartItemId() {
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
    void testGetCartItemByIdInvalidId() throws CartException {
        Integer cartItemId = -1; // Example invalid cart item ID

        Optional<CartItem> result = cartService.getCartItemById(cartItemId);

        assertTrue(result.isEmpty());
    }

// getCartByCustomerId
    @Test
    @DisplayName("Get Cart By Customer Id - Invalid Customer Id")
    void testGetCartByCustomerIdInvalidId() {
        Integer customerId = 0; // Example invalid customer ID

        CartException exception = assertThrows(CartException.class, () -> {
            cartService.getCartByCustomerId(customerId);
        });

        // Assert the exception message
        assertEquals("Invalid customer Id:" + customerId, exception.getMessage());
    }


}
