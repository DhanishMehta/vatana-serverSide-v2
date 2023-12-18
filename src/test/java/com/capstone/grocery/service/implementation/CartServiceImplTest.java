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

import com.capstone.grocery.model.Cart;
import com.capstone.grocery.model.CartItem;
import com.capstone.grocery.model.User;
import com.capstone.grocery.model.UserRole;
import com.capstone.grocery.model.product.Discount;
import com.capstone.grocery.model.product.Offer;
import com.capstone.grocery.model.product.Pricing;
import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CartServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    User user_1 = new User("test_1", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    User user_2 = new User("test_2", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    User user_3 = new User("test_3", "test", "test", "test", "test", "test", null, UserRole.USER, null, null);
    Product product_1 = new Product("id_1", "desc_1", "sku_max_quantity_1", "pack_desc_1", 0, 0, false, "weight",
            "absolute_url", "usp", null,
            new Pricing(new Discount("10", "10", "10", "10", "10", "10"),
                    new Offer("null", false, "null", "null", "null", "null", "null", "null", "null", "null", "null")),
            null, "variableWeight", null, null, null, null, null, null);
    Product product_2 = new Product("id_2", "desc_2", "sku_max_quantity_2", "pack_desc_2", 0, 0, false, "weight",
            "absolute_url", "usp", null, new Pricing(new Discount("10", "10", "10", "10", "10", "10"),
                    new Offer("null", false, "null", "null", "null", "null", "null", "null", "null", "null", "null")),
            null, "variableWeight", null, null, null, null, null, null);
    Product product_3 = new Product("id_3", "desc_3", "sku_max_quantity_3", "pack_desc_3", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartServiceImpl).build();
    }

    @Test
    void addProductToCart_userNotFound_404() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = cartServiceImpl.addProductToCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void addProductToCart_productNotFound_404() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = cartServiceImpl.addProductToCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void addProductToCart_userCartIsNull() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.addProductToCart(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Added to Cart", response.getMessage());
    }

    @Test
    void addProductToCart_userCartIsNotNull_AddSameProduct_shouldIncreaseQuantityOfSameProduct() {
        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(new CartItem(product_1, 1)));
        Cart newCart = new Cart(cartItems, Integer.parseInt(product_1.getPricing().getDiscount().getMrp()));
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.addProductToCart(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Added to Cart", response.getMessage());
        assertEquals(1, response.getData().getCart().getCartItems().size());
        assertEquals(2, response.getData().getCart().getCartItems().get(0).getCartItemQuantity());
    }
    
    @Test
    void addProductToCart_userCartIsNotNull_AddDifferentProduct_shouldIncreaseCartItems() {
        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(new CartItem(product_1, 1)));
        Cart newCart = new Cart(cartItems, Integer.parseInt(product_1.getPricing().getDiscount().getMrp()));
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_2.getId())).thenReturn(Optional.of(product_2));
        CommonResponse<User> response = cartServiceImpl.addProductToCart(product_2.getId(), user_1.getUserId());
        assertEquals("Product Added to Cart", response.getMessage());
        assertEquals(200, response.getStatusCode());
        assertEquals(2, response.getData().getCart().getCartItems().size());
        assertEquals(1, response.getData().getCart().getCartItems().get(0).getCartItemQuantity());
        assertEquals(1, response.getData().getCart().getCartItems().get(1).getCartItemQuantity());
    }
    
    @Test
    void removeProductFromCart_userNotFound_404() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void removeProductFromCart_productNotFound_404() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void removeProductFromCart_userCartIsNull() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
        assertEquals("Cart is Empty", response.getMessage());
    }

    @Test
    void removeProductFromCart_userCartItemsIsNull() {
        Cart newCart = new Cart();
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
        assertEquals("Cart is Empty", response.getMessage());
    }
    
    @Test
    void removeProductFromCart_productNotFoundInCart() {
        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(new CartItem(product_1, 1)));
        Cart newCart = new Cart(cartItems, Integer.parseInt(product_1.getPricing().getDiscount().getMrp()));
        user_1.setCart(newCart);
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_2.getId())).thenReturn(Optional.of(product_2));
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_2.getId(), user_1.getUserId());
        assertEquals(404, response.getStatusCode());
        assertEquals("Product Not Found in Cart", response.getMessage());
    }

    @Test
    void removeProductFromCart_productQuantityIsOne_deleteProduct() {
        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(new CartItem(product_1, 1)));
        Cart newCart = new Cart(cartItems, Integer.parseInt(product_1.getPricing().getDiscount().getMrp()));
        user_1.setCart(newCart);
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Removed from Cart", response.getMessage());
        assertEquals(0, response.getData().getCart().getCartItems().size());
    }

    @Test
    void removeProductFromCart_productQuantityIsMoreThanOne_decreaseQuantityOfProduct() {
        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(new CartItem(product_1, 2)));
        Cart newCart = new Cart(cartItems, Integer.parseInt(product_1.getPricing().getDiscount().getMrp()));
        user_1.setCart(newCart);
        user_1.setCart(newCart);
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<User> response = cartServiceImpl.removeProductFromCart(product_1.getId(), user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Product Removed from Cart", response.getMessage());
        assertEquals(1, response.getData().getCart().getCartItems().size());
        assertEquals(1, response.getData().getCart().getCartItems().get(0).getCartItemQuantity());
    }
    
    @Test
    void clearCartTest_success() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        CommonResponse<User> response = cartServiceImpl.clearCart(user_1.getUserId());
        assertEquals(200, response.getStatusCode());
        assertEquals("Cart cleared", response.getMessage());
    }
    
    @Test
    void clearCartTest_failure() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = cartServiceImpl.clearCart(user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
}
