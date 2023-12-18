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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ProductServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    Product product_1 = new Product("id_1", "desc_1", "sku_max_quantity_1", "pack_desc_1", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_2 = new Product("id_2", "desc_2", "sku_max_quantity_2", "pack_desc_2", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_3 = new Product("id_3", "desc_3", "sku_max_quantity_3", "pack_desc_3", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productServiceImpl).build();
    }

    @Test
    void testGetAllProducts_success() {
        int page = 1;
        int limit = 10;
        List<Product> productsList = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        Pageable customPage = PageRequest.of(page-1, limit);
        Page<Product> products = new PageImpl<>(productsList, customPage, limit);

        Mockito.when(productRepository.findAll(customPage)).thenReturn(products);
        Mockito.when(productRepository.count()).thenReturn(3L);
        CommonResponse<Page<Product>> response = productServiceImpl.getAllProducts(limit, page);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testGetAllProducts_failure() {
        int page = 1;
        int limit = 10;
        Pageable customPage = PageRequest.of(page-1, limit);
        
        Mockito.when(productRepository.findAll(customPage)).thenThrow(new NullPointerException());
        CommonResponse<Page<Product>> response = productServiceImpl.getAllProducts(limit, page);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testGetProductById_success() {
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<Product> response = productServiceImpl.getProductById(product_1.getId());
        assertEquals(200, response.getStatusCode());
        assertEquals(product_1.getId(), response.getData().getId());
    }

    @Test
    void testGetProductById_failure() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<Product> response = productServiceImpl.getProductById(null);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testAddProduct_success() {
        Mockito.when(productRepository.save(product_1)).thenReturn(product_1);
        CommonResponse<Product> response = productServiceImpl.addProduct(product_1);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testAddProduct_failure() {
        Mockito.when(productRepository.save(product_1)).thenThrow(new NullPointerException());
        CommonResponse<Product> response = productServiceImpl.addProduct(product_1);
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testAddProductInBulk_success() {
        List<Product> products = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        Mockito.when(productRepository.save(product_1)).thenReturn(product_1);
        CommonResponse<List<Product>> response = productServiceImpl.addProductInBulk(products);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testAddProductInBulk_failure() {
        List<Product> products = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        Mockito.when(productRepository.save(product_1)).thenThrow(new NullPointerException());
        CommonResponse<List<Product>> response = productServiceImpl.addProductInBulk(products);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testUpdateProduct_success() {
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        Mockito.when(productRepository.save(product_1)).thenReturn(product_1);
        CommonResponse<Product> response = productServiceImpl.updateProduct(product_1.getId(), product_1);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testUpdateProduct_failure() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<Product> response = productServiceImpl.updateProduct(product_1.getId(), product_1);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_success() {
        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        CommonResponse<Product> response = productServiceImpl.deleteProduct(product_1.getId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testDeleteProduct_failure() {
        Mockito.when(productRepository.findById(product_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<Product> response = productServiceImpl.deleteProduct(product_1.getId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testDeleteAllProducts_success() {
        CommonResponse<List<Product>> response = productServiceImpl.deleteAllProduct();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testFindProductBySearchParam_success() {
        CommonResponse<Page<Product>> response1 = productServiceImpl.findProductsBySearchParams(null, null, null, null, null);
        CommonResponse<Page<Product>> response2 = productServiceImpl.findProductsBySearchParams("test", 10, 2, "abc", "price-htl");
        CommonResponse<Page<Product>> response3 = productServiceImpl.findProductsBySearchParams("test", 10, 2, "abc", "price-lth");
        CommonResponse<Page<Product>> response4 = productServiceImpl.findProductsBySearchParams("test", 10, 2, "abc", "rating-htl");
        CommonResponse<Page<Product>> response5 = productServiceImpl.findProductsBySearchParams("test", 10, 2, "abc", "rating-lth");
        CommonResponse<Page<Product>> response6 = productServiceImpl.findProductsBySearchParams("test", 10, 2, "", "rating-lth");
        assertEquals(response1.getStatusCode(), 200);
        assertEquals(response2.getStatusCode(), 200);
        assertEquals(response3.getStatusCode(), 200);
        assertEquals(response4.getStatusCode(), 200);
        assertEquals(response5.getStatusCode(), 200);
        assertEquals(response6.getStatusCode(), 200);
    }
    
    @Test
    void testFindProductBySearchParam_failure() {
        Sort customSort = Sort.unsorted();
        Pageable customPage = PageRequest.of(1, 10, customSort);
        Mockito.when(productRepository.findBySearchParam("test", customPage)).thenThrow(new NullPointerException());
        CommonResponse<Page<Product>> response1 = productServiceImpl.findProductsBySearchParams("test", 10, 2, null, null);
        assertEquals(404, response1.getStatusCode());
    }
}
