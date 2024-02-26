package com.project.carstore.cart;

import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.exceptions.ProductException;
import com.project.carstore.product.Product;
import com.project.carstore.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private CustomerRepository customerRepository;

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
    public Cart addCartItemToCart(CartItemDTO cartItemDTO) throws CartException {
        //productId , CustomerId
        // values for cartItem : quantity ,Total price ,cartId,productId
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
        Optional<Product> findProduct = null;
        try {
            findProduct = this.productService.getProductById(cartItemDTO.getProductId());
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
        //check if product already in cart
        Optional<CartItem> findCartItemByPId = getCartItemByProductId(cartItemDTO.getProductId());
        if (findCartItemByPId.isPresent()) {
            Integer quantity = findCartItemByPId.get().getQuantity();
            if(findProduct.get().getQuantity()>0) {
                findCartItemByPId.get().setQuantity(quantity + 1);
                Double totalPrice = findCartItemByPId.get().getTotalPrice();
                findCartItemByPId.get().setTotalPrice(totalPrice + findProduct.get().getPrice());
                this.cartItemRepository.save(findCartItemByPId.get());
                cartItemSet.add(findCartItemByPId.get());
            }else throw new CartException("Insufficient Product Stock");
        } else {
            //create CartItem
            if(findProduct.get().getQuantity()>0) {
                CartItem cartItem = new CartItem(1, cartItemDTO.getCartId(), cartItemDTO.getProductId(), findProduct.get().getPrice());
                this.cartItemRepository.save(cartItem);
                cartItemSet.add(cartItem);
            }else throw new CartException("Insufficient Product Stock");
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
    @Override
    public Optional<Cart> getCartByCustomerId(Integer customerId) throws CartException{
        Optional<Customer> findCustomer= this.customerRepository.findById(customerId);
        if(findCustomer.isPresent())
        {
            Integer cartId=findCustomer.get().getCartId();
            Optional<Cart> findCart=this.getCartById(cartId);
            if(findCart.isPresent())
            {
                return findCart;
            }
            else throw new CartException("No cart exist with Id:"+cartId);
        }
        else throw new CartException("No customer exist with Id:"+customerId);
    }
    @Override
    public String clearCart(Integer customerId) throws CartException {
        Optional<Customer> findCustomer=this.customerRepository.findById(customerId);
        if(findCustomer.isPresent())
        {
            Optional<Cart> findCart=this.getCartById(findCustomer.get().getCartId());
            if (findCart.isPresent()){
                //clear cartItems from cartItem Repository
                Set<CartItem> cartItemList=findCart.get().getCartItems();
                this.cartItemRepository.deleteAll(cartItemList);
                cartItemList.clear();
                findCart.get().setCartItems(cartItemList);
                findCart.get().setTotalItems(0);
                findCart.get().setTotalPrice(0.0);
                this.cartRepository.save(findCart.get());
                return "Cart Cleared Successfully for Customer:"+String.valueOf(customerId);
            }else throw new CartException("Cart does not exist for Customer:"+customerId);
        }else throw new CartException("No customer exist with Id:"+customerId);
    }
    @Override
    public Cart checkOut() {



        return null;
    }
    @Override
    public Cart updateCartItem(UpdateCartItemDTO updateCartItemDTO) throws CartException, ProductException {
        // get cart using cart id
        Optional<Cart> findCart= this.getCartById(updateCartItemDTO.getCartId());
        if(findCart.isPresent())
        {
            Optional<CartItem> findCartItem =this.getCartItemById(updateCartItemDTO.getCartItemId());
            Optional<Product> findProduct=this.productService.getProductById(findCartItem.get().getProductId());
            if(findCartItem.isPresent())
            {
                if(updateCartItemDTO.getNewQuantity()!=0 && updateCartItemDTO.getNewQuantity()!=null && findProduct.get().getQuantity()>=updateCartItemDTO.getNewQuantity()) {
                    findCartItem.get().setQuantity(updateCartItemDTO.getNewQuantity());
                    Double productPrice;
                    if(findProduct.isPresent())
                    {
                        productPrice=findProduct.get().getPrice();
                    } else throw new ProductException("Cant get Product Price");
                    findCartItem.get().setTotalPrice(findCartItem.get().getQuantity()*productPrice);
                    this.cartItemRepository.save(findCartItem.get());
                    Set<CartItem> cartItemSet=findCart.get().getCartItems();
                    cartItemSet.add(findCartItem.get());
                    findCart.get().setCartItems(cartItemSet);
                    return this.cartRepository.save(findCart.get());
                } else throw new CartException("Invalid Quantity or Insufficient Stock:"+updateCartItemDTO.getNewQuantity());
            } else throw new CartException("CartItem does not exist for cartItemId: "+updateCartItemDTO.getCartItemId());
        } else throw new CartException("Cart does not exist for CartId: "+updateCartItemDTO.getCartId());
    }
    @Override
    public String removeCartItem(Integer cartItemId) throws CartException{
            Optional<CartItem> findCartItem=this.getCartItemById(cartItemId);
            if(findCartItem.isPresent())
            {
                Optional<Cart> findCart=this.getCartById(findCartItem.get().getCartId());
                if(findCart.isPresent())
                {
                    //remove cartItem from cart
                    findCart.get().getCartItems().remove(findCartItem.get());
                    this.cartRepository.save(findCart.get());
                    this.cartItemRepository.delete(findCartItem.get());
                    return "cart Item Removed Successfully";
                }else throw new CartException("Cart does not exist for cartId: "+findCartItem.get().getCartId());
            }else throw new CartException("CartItem does not exist for cartItemId: "+cartItemId);
    }
    @Override
    public Set<CartItem> getAllCartItems(Integer cartId) throws CartException{
        Optional<Cart> findCart = cartRepository.findById(cartId);
        if(findCart.isPresent()){
            return findCart.get().getCartItems();
        }else throw new CartException("Cart id does not exist");

    }
    @Override
    public Optional<CartItem> getCartItemById(Integer cartItemId) {
        return this.cartItemRepository.findById(cartItemId);
    }
    @Override
    public Double getCartTotal(Integer cartId) throws CartException{
        Optional<Cart> findCart = cartRepository.findById(cartId);
        if(findCart.isPresent()){
            return findCart.get().getTotalPrice();
        }else throw new CartException("No cart present for the cart Id");
    }
}