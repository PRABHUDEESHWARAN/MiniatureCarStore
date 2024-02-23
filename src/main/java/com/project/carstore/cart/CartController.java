package com.project.carstore.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;



    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Integer cartId, @RequestBody CartItem cartItem) {
        try {
            Cart updatedCart = cartService.removeItemFromCart(cartId, cartItem);
            if (updatedCart != null) {
                return ResponseEntity.ok(updatedCart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing item from cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> clearCartItems(@PathVariable Integer cartId) {
        try {
            Cart updatedCart = cartService.clearCartItems(cartId);
            if (updatedCart != null) {
                return ResponseEntity.ok(updatedCart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing cart: " + e.getMessage());
        }
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> updateItemQuantity(@PathVariable Integer cartId,
                                                @PathVariable Integer itemId,
                                                @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.updateItemQuantity(cartId, itemId, quantity);
            if (updatedCart != null) {
                return ResponseEntity.ok(updatedCart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating item quantity: " + e.getMessage());
        }
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<?> viewCartItems(@PathVariable Integer cartId) {
        try {
            Map<Integer, CartItem> cartItems = cartService.viewCartItems(cartId);
            if (cartItems != null) {
                return ResponseEntity.ok(cartItems);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error viewing cart items: " + e.getMessage());
        }
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<?> calculateTotalCartValue(@PathVariable Integer cartId) {
        try {
            Integer totalValue = cartService.calculateTotalCartValue(cartId);
            if (totalValue != null) {
                return ResponseEntity.ok(totalValue);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calculating total cart value: " + e.getMessage());
        }
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<?> checkoutCart(@PathVariable Integer cartId) {
        try {
            boolean checkoutStatus = cartService.checkoutCart(cartId);
            if (checkoutStatus) {
                return ResponseEntity.ok("Checkout successful");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Checkout failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking out cart: " + e.getMessage());
        }
    }
}




