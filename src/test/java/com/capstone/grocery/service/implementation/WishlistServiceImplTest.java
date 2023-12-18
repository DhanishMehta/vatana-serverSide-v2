package com.capstone.grocery.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.User;
import com.capstone.grocery.model.UserRole;
import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class WishlistServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishlistServiceImpl wishlistServiceImpl;

    User user_1 = new User("test_1", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    User user_2 = new User("test_2", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    User user_3 = new User("test_3", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    Product product_1 = new Product("id_1", "desc_1", "sku_max_quantity_1", "pack_desc_1", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_2 = new Product("id_2", "desc_2", "sku_max_quantity_2", "pack_desc_2", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_3 = new Product("id_3", "desc_3", "sku_max_quantity_3", "pack_desc_3", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(wishlistServiceImpl).build();
    }

    @Test
    void addProductToWishlist_userNotFound_404() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = wishlistServiceImpl.addProductToWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void addProductToWishlist_productNotFound_404() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = wishlistServiceImpl.addProductToWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void addProductToWishlist_WishlistIsNull() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = wishlistServiceImpl.addProductToWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Added in wishlist", response.getMessage());
    }

    @Test
    void addProductToWishlist_WishlistIsNotNullAndProductAlreadyInWishlist() {
        List<Product> products = new ArrayList<>(Arrays.asList(product_1, product_2));
        user_1.setWishlist(products);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = wishlistServiceImpl.addProductToWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product already in wishlist", response.getMessage());
    }

    @Test
    void removeProductFromWishlist_userNotFound_404() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = wishlistServiceImpl.removeProductFromWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void removeProductFromWishlist_productNotFound_404() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = wishlistServiceImpl.removeProductFromWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void removeProductFromWishlist_WishlistIsNull_productNotFoundInWishlist() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        CommonResponse<User> response = wishlistServiceImpl.removeProductFromWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
        assertEquals("Product not found in wishlist", response.getMessage());
    }

    @Test
    void removeProductFromWishlist_WishlistIsNotNullAndProductPresentInWishlist() {
        List<Product> products = new ArrayList<>(Arrays.asList(product_1, product_2));
        user_1.setWishlist(products);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        CommonResponse<User> response = wishlistServiceImpl.removeProductFromWishlist(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Deleted from wishlist", response.getMessage());
        assertEquals(1, response.getData().getWishlist().size());
    }
}
