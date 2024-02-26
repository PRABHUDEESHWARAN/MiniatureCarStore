package com.project.carstore.cart;

import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.exceptions.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.carstore.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerRepository customerRepository;
    @PostMapping("/addCartItem")
    public Cart addCartItemToCart(@RequestBody CartItemDTO cartItemDTO) throws CartException
    {
        return this.cartService.addCartItemToCart(cartItemDTO);
    }
    @GetMapping("/{customerId}")
    public Cart getCartByCustomerId(@PathVariable("customerId") Integer customerId) throws CartException
    {
        Optional<Cart> findCart=this.cartService.getCartByCustomerId(customerId);
        if(findCart.isPresent())
        {
            return findCart.get();
        }else throw new CartException("Invalid Customer Id");
    }

    @DeleteMapping("/clear/{customerId}")
    public String clearCustomerCart(@PathVariable("customerId") Integer customerId) throws CartException
    {
        return this.cartService.clearCart(customerId);

    }

    @GetMapping("/cartTotal/{cartId}")
    public Double getCartTotal(@PathVariable("cartId") Integer cartId) throws CartException {
        return this.cartService.getCartTotal(cartId);
    }

    @PatchMapping("/update-cart")
    public Cart updateCartItem(@RequestBody UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException {
        return this.cartService.updateCartItem(updateCartItemDTO);
    }

    @DeleteMapping("/cartItem/del/{cartItemId}")
    public String removeCartItem(@PathVariable("cartItemId") Integer cartItemId) throws CartException
    {
        return this.cartService.removeCartItem(cartItemId);
    }

    @GetMapping("/cartItems/{cartId}")
    public Set<CartItem> getAllCartItems(@PathVariable("cartId") Integer cartId) throws CartException{
        return this.cartService.getAllCartItems(cartId);
    }
}



