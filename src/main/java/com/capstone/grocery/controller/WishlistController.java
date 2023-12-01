package com.capstone.grocery.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.User;
import com.capstone.grocery.model.UserAndProduct;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.WishlistService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public CommonResponse<User> addProductToWishlist(@RequestBody UserAndProduct userAndProduct){
        return wishlistService.addProductToWishlist(userAndProduct.getProductId(), userAndProduct.getUserId());
    }

    @DeleteMapping("/{productId}/{userId}")
    public CommonResponse<User> removeProductFromWishlist(@PathVariable String productId, @PathVariable String userId){
        return wishlistService.removeProductFromWishlist(productId, userId);
    }
}
