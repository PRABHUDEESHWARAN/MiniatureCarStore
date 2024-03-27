package com.project.carstore.cart;

import com.project.carstore.exceptions.ProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/item")
    public Cart addCartItemToCart(@RequestBody CartItemDTO cartItemDTO) throws CartException, ProductException {
        return this.cartService.addCartItemToCart(cartItemDTO);
    }

    @GetMapping("/{customerId}")
    public Cart getCartByCustomerId(@PathVariable("customerId") Integer customerId) throws CartException {
        Optional<Cart> findCart = this.cartService.getCartByCustomerId(customerId);
        if (findCart.isPresent()) {
            return findCart.get();
        } else throw new CartException("Invalid Customer Id");
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> clearCustomerCart(@PathVariable("customerId") Integer customerId) throws CartException {
        return ResponseEntity.ok(this.cartService.clearCart(customerId));

    }

    @GetMapping("/cartTotal/{cartId}")
    public Double getCartTotal(@PathVariable("cartId") Integer cartId) throws CartException {
        return this.cartService.getCartTotal(cartId);
    }

    @PatchMapping("/update")
    public Cart updateCartItem(@RequestBody UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException {
        return this.cartService.updateCartItem(updateCartItemDTO);
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable("cartItemId") Integer cartItemId) throws CartException {
        return ResponseEntity.ok(this.cartService.removeCartItem(cartItemId));
    }
    @PatchMapping("/cartItem/{cartItemId}")
    public ResponseEntity<Cart> reduceCartItem(@PathVariable("cartItemId") Integer cartItemId) throws CartException,ProductException{
        return ResponseEntity.ok(this.cartService.reduceCartItem(cartItemId));

    }
    @GetMapping("/cartItems/{cartId}")
    public Set<CartItem> getAllCartItems(@PathVariable("cartId") Integer cartId) throws CartException {
        return this.cartService.getAllCartItems(cartId);
    }
}



