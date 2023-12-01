package com.capstone.grocery.service;

import com.capstone.grocery.model.User;
import com.capstone.grocery.response.CommonResponse;

public interface WishlistService {

    public CommonResponse<User> addProductToWishlist(String productId, String userId);
    public CommonResponse<User> removeProductFromWishlist(String productId, String userId);

}
