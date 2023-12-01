package com.capstone.grocery.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.User;
import com.capstone.grocery.model.UserAndProduct;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CartService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    
    private final CartService cartService;

    @PostMapping
    public CommonResponse<User> addProductToCart(@RequestBody UserAndProduct userAndProduct){
        return cartService.addProductToCart(userAndProduct.getProductId(), userAndProduct.getUserId());
    }

    @DeleteMapping("/{productId}/{userId}")
    public CommonResponse<User> removeProductFromCart(@PathVariable String productId, @PathVariable String userId){
        return cartService.removeProductFromCart(productId, userId);
    }

    @PutMapping("/clear")
    public CommonResponse<User> clearCart(@RequestParam String user) {
        return cartService.clearCart(user);
    }
}
