package com.capstone.grocery.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.response.Pagination;
import com.capstone.grocery.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class ProductControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    Product product_1 = new Product("id_1", "desc_1", "sku_max_quantity_1", "pack_desc_1", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_2 = new Product("id_2", "desc_2", "sku_max_quantity_2", "pack_desc_2", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);
    Product product_3 = new Product("id_3", "desc_3", "sku_max_quantity_3", "pack_desc_3", 0, 0, false, "weight",
            "absolute_url", "usp", null, null, null, "variableWeight", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts_shouldReturnPageWithProducts() throws Exception {
        // arrange
        int page = 0;
        int limit = 10;
        List<Product> productsList = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        Pageable customPage = PageRequest.of(page, limit);
        Page<Product> products = new PageImpl<>(productsList, customPage, limit);
        Long total = 3L;
        Long totalPages = 1L;
        CommonResponse<Page<Product>> expectedResponse = new CommonResponse<Page<Product>>(200, true,
                "Products List",
                new Pagination(page, limit, totalPages, total), products);

        Mockito.when(productService.getAllProducts(limit, page)).thenReturn(expectedResponse);

        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("limit", "10"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", hasSize(3)))
                .andExpect(jsonPath("$.data.content[0].id", is(product_1.getId())));
    }

    @Test
    void getProductById_success() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(200, true, "Products List", null,
                product_1);

        Mockito.when(productService.getProductById(product_1.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/{productId}", product_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(product_1.getId())));
    }

    @Test
    void getProductById_failure() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(404, false, "Products List",
                null, null);

        Mockito.when(productService.getProductById("randomProduct")).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/{productId}", "randomProduct")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.statusCode", is(404)));
    }

    @Test
    void addProduct_success() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(200, true, "Products List", null,
                product_3);

        Mockito.when(productService.addProduct(product_3)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(product_3)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(product_3.getId())));
    }

    @Test
    void addProduct_failure() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(404, false, "Products List",
                null,
                null);
        Product newProduct = new Product();
        Mockito.when(productService.addProduct(newProduct)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(newProduct)))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void addProductInBulk_success() throws Exception {
        List<Product> products = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        CommonResponse<List<Product>> expectedResponse = new CommonResponse<List<Product>>(200, true,
                "Products List",
                null,
                products);

        Mockito.when(productService.addProductInBulk(products)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(products)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", is(product_1.getId())));
    }

    @Test
    void addProductInBulk_failure() throws Exception {
        Product newProduct = new Product();
        List<Product> products = new ArrayList<>(Arrays.asList(newProduct, newProduct, newProduct));
        CommonResponse<List<Product>> expectedResponse = new CommonResponse<List<Product>>(404, false,
                "Products List",
                null,
                null);
        Mockito.when(productService.addProductInBulk(products)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(products)))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void updateProduct_success() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(200, true, "Products List", null,
                product_2);

        Mockito.when(productService.updateProduct(product_2.getId(), product_2)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/{productId}", product_2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(product_2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(product_2.getId())))
                .andExpect(jsonPath("$.data.desc", is(product_2.getDesc())));
    }

    @Test
    void updateProduct_failure() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(404, false, "Products List",
                null, null);

        Mockito.when(productService.updateProduct(product_1.getId(), product_2)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/{productId}", product_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(product_2)))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void deleteProduct_success() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(200, true, "Products List", null,
                product_2);
        Mockito.when(productService.deleteProduct(product_2.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/{productId}", product_2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(product_2.getId())));
    }

    @Test
    void deleteProduct_failure() throws Exception {
        CommonResponse<Product> expectedResponse = new CommonResponse<Product>(404, false, "Products List",
                null, null);
        Mockito.when(productService.deleteProduct(product_1.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/{productId}", product_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void deleteAllProducts_success() throws Exception {
        CommonResponse<List<Product>> expectedResponse = new CommonResponse<List<Product>>(200, true,
                "Products List", null, null);
        Mockito.when(productService.deleteAllProduct()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void deleteAllProducts_failure() throws Exception {
        CommonResponse<List<Product>> expectedResponse = new CommonResponse<List<Product>>(404, false,
                "Products List", null, null);
        Mockito.when(productService.deleteAllProduct()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void findProductsBySearchParam() throws Exception {
        // arrange
        int page = 0;
        int limit = 10;
        String category = "testCategory";
        String sortBy = "price-htl";
        String searchQuery = "testSearch";
        List<Product> productsList = new ArrayList<>(Arrays.asList(product_1, product_2, product_3));
        Pageable customPage = PageRequest.of(page, limit);
        Page<Product> products = new PageImpl<>(productsList, customPage, limit);
        Long total = 3L;
        Long totalPages = 1L;
        CommonResponse<Page<Product>> expectedResponse = new CommonResponse<Page<Product>>(200, true,
                "Products List",
                new Pagination(page, limit, totalPages, total), products);

        Mockito.when(productService.findProductsBySearchParams(searchQuery, limit, page, category, sortBy))
                .thenReturn(expectedResponse);

        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("q", searchQuery)
                .param("category", category)
                .param("sortBy", sortBy)
                .param("page", Integer.toString(page))
                .param("limit", Integer.toString(limit)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", hasSize(3)))
                .andExpect(jsonPath("$.data.content[0].id", is(product_1.getId())));
    }
}