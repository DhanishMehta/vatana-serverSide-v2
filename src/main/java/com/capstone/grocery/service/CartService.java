package com.capstone.grocery.service;

import com.capstone.grocery.model.User;
import com.capstone.grocery.response.CommonResponse;

public interface CartService {
    
    public CommonResponse<User> addProductToCart(String productId, String userId);
    public CommonResponse<User> removeProductFromCart(String productId, String userId);
    public CommonResponse<User> clearCart(String userId);

}