package com.project.carstore.cart;

import com.project.carstore.product.Product;
import com.project.carstore.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Optional<Cart> getCartById(Integer cartId) throws CartException {
        if(this.cartRepository.existsById(cartId))
        {
            return this.cartRepository.findById(cartId);
        }
        else throw new CartException("No cart exist with Id:"+cartId);
    }

    @Override
    public Integer CreateCartForCustomer(Integer customerId) {
        System.out.println("is this null:"+customerId);
        Cart newCustomerCart=new Cart(customerId);
        System.out.println("this is customer Id:"+newCustomerCart.getCustomerId());
        newCustomerCart=this.cartRepository.save(newCustomerCart);
        return newCustomerCart.getId();
    }

    @Override
    public Cart addCartItemToCart(CartItemDTO cartItemDTO) {
        //productId , CustomerId
        // values for cartItem : quantity ,Totalprice ,cartId,productId
        //get Customer
        //Optional<Customer> findCustomer = this.customerService.getCustomerById(cartItemDTO.getCustomerId());
        //get Cart
        Optional<Cart> cart=Optional.empty();
        try {
             cart= this.getCartById(cartItemDTO.getCartId());
        } catch (CartException e) {
            System.out.println(e.getMessage());
        }
        // prepare cartItemSet to add it to cart
        Set<CartItem> cartItemSet=cart.get().getCartItems();
        //get the product
        Optional<Product> findProduct = this.productService.getProductById(cartItemDTO.getProductId());
        //check if product already in cart
        Optional<CartItem> findCartItemByPId = getCartItemByProductId(cartItemDTO.getProductId());
        if (findCartItemByPId.isPresent()) {
            Integer quantity = findCartItemByPId.get().getQuantity();
            findCartItemByPId.get().setQuantity(quantity + 1);
            Double totalPrice = findCartItemByPId.get().getTotalPrice();
            findCartItemByPId.get().setTotalPrice(totalPrice + findProduct.get().getPrice());
            this.cartItemRepository.save(findCartItemByPId.get());
            cartItemSet.add(findCartItemByPId.get());
        } else {
            //create CartItem
            CartItem cartItem = new CartItem(1, cartItemDTO.getCartId(), cartItemDTO.getProductId(), findProduct.get().getPrice());
            this.cartItemRepository.save(cartItem);
            cartItemSet.add(cartItem);
        }
        //  add cartItem to cart and save Cart
        cart.get().setCartItems(cartItemSet);
        cart.get().setTotalItems(cartItemSet.size());
        AtomicReference<Double> totalPriceOfCart= new AtomicReference<>(0.0);
        cartItemSet.stream().map(p->p.getTotalPrice()).forEach(p-> totalPriceOfCart.updateAndGet(v -> v + p));
        cart.get().setTotalPrice(totalPriceOfCart.get());
        return this.cartRepository.save(cart.get());
    }
    @Override
    public Optional<CartItem> getCartItemByProductId(Long productId) {
        return this.cartItemRepository.findCartItemByProductId(productId);
    }
}
