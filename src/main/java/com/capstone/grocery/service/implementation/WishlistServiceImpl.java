package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.grocery.model.User;
import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.WishlistService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CommonResponse<User> addProductToWishlist(String productId, String userId) {
        try {
            User user = this.userRepository.findById(userId).get();
            Product product = this.productRepository.findById(productId).get();

            List<Product> newWishlist = new ArrayList<>();
            if (user.getWishlist() == null) {
                user.setWishlist(newWishlist);
            }

            newWishlist = user.getWishlist();
            int index = -1;
            for (Product item : newWishlist) {
                if (item.getId().equals(productId)) {
                    index = newWishlist.indexOf(item);
                    break;
                }
            }

            if (index != -1) {
                return Utility.getCommonResponse(200, true, "Product already in wishlist", null, user);
            }

            newWishlist.add(product);
            user.setWishlist(newWishlist);
            userRepository.save(user);
            return Utility.getCommonResponse(200, true, "Product Added in wishlist", null, user);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to add product to wishlist due to Error: " + exc, null,
                    null);
        }
    }

    @Override
    public CommonResponse<User> removeProductFromWishlist(String productId, String userId) {
        try {
            User user = this.userRepository.findById(userId).get();

            List<Product> newWishlist = new ArrayList<>();
            if (user.getWishlist() == null) {
                user.setWishlist(newWishlist);
            }

            newWishlist = user.getWishlist();
            int index = -1;
            for (Product item : newWishlist) {
                if (item.getId().equals(productId)) {
                    index = newWishlist.indexOf(item);
                    break;
                }
            }

            if (index == -1) {
                return Utility.getCommonResponse(404, false, "Product not found in wishlist", null, user);
            }

            newWishlist.remove(index);
            user.setWishlist(newWishlist);
            userRepository.save(user);
            return Utility.getCommonResponse(200, true, "Product Deleted from wishlist", null, user);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to add product to wishlist due to Error: " + exc, null,
                    null);
        }
    }

}
