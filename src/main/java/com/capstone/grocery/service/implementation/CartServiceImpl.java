package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.grocery.model.Cart;
import com.capstone.grocery.model.CartItem;
import com.capstone.grocery.model.User;
import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CartService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Override
    public CommonResponse<User> addProductToCart(String productId, String userId) {
        try {
            User user = userRepository.findById(userId).get();
            Product product = productRepository.findById(productId).get();

            Cart newCart = new Cart();
            if(user.getCart() == null){
                user.setCart(newCart);
            }
            if(user.getCart().getCartItems() == null){
                user.getCart().setCartItems(new ArrayList<>());
            }
            List<CartItem> cartItems = user.getCart().getCartItems();
            
            int index = -1;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getCartItemProduct().getId().equals(productId)) {
                    index = cartItems.indexOf(cartItem);
                    break;
                }
            }

            if (index != -1) {
                CartItem newCartItem = new CartItem(product, cartItems.get(index).getCartItemQuantity() + 1);
                cartItems.set(index, newCartItem);
                
            } else {
                CartItem newCartItem = new CartItem(product, 1);
                cartItems.add(newCartItem);
            }
            
            newCart.setCartItems(cartItems);
            newCart.setCartTotal(newCart.getCartTotal());
            
            user.setCart(newCart);
            userRepository.save(user);
            return Utility.getCommonResponse(200, true, "Product Added to Cart", null, user);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product/User Not Found.. Error: "+exc, null, null);
        }
    }
    
    @Override
    public CommonResponse<User> removeProductFromCart(String productId, String userId) {
        try {
            User user = userRepository.findById(userId).get();
            Product product = productRepository.findById(productId).get();
            
            Cart newCart = new Cart();
            if(user.getCart() == null){
                return Utility.getCommonResponse(404, false, "Cart is Empty", null, null);
            }
            if(user.getCart().getCartItems() == null){
                return Utility.getCommonResponse(404, false, "Cart is Empty", null, null);
            }
            List<CartItem> cartItems = user.getCart().getCartItems();
            
            int index = -1;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getCartItemProduct().getId().equals(productId)) {
                    index = cartItems.indexOf(cartItem);
                    break;
                }
            }

            if (index == -1) {
                return Utility.getCommonResponse(404, false, "Product Not Found in Cart", null, null);
            }

            if (cartItems.get(index).getCartItemQuantity() == 1) {
                cartItems.remove(index);
            } else {
                CartItem newCartItem = new CartItem(product, cartItems.get(index).getCartItemQuantity() - 1);
                cartItems.set(index, newCartItem);
            }

            newCart.setCartItems(cartItems);
            newCart.setCartTotal(newCart.getCartTotal());

            user.setCart(newCart);
            userRepository.save(user);
            return Utility.getCommonResponse(200, true, "Product Removed from Cart", null, user);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product/User Not Found!! Error: "+exc, null, null);
        }
    }
    
    @Override
    public CommonResponse<User> clearCart(String userId) {
        try{
            User user = this.userRepository.findById(userId).get();
            user.setCart(new Cart(new ArrayList<>(), 0.0));
            userRepository.save(user);
            return Utility.getCommonResponse(200, true, "Cart cleared", null, null);
        } catch (Exception exc) {   
            return Utility.getCommonResponse(404, false, "User Not Found!! Error: "+exc, null, null);
        }
    }

}
