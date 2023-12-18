package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.CategoryOfTree;
import com.capstone.grocery.repository.CategoryRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class CategoryServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    CategoryOfTree category_1 = new CategoryOfTree(1, "testCategory_1", "test-category-1", "test-url-1", 0, null, null, null, null, null);
    CategoryOfTree category_2 = new CategoryOfTree(2, "testCategory_2", "test-category-2", "test-url-2", 0, null, null, null, null, null);
    CategoryOfTree category_3 = new CategoryOfTree(3, "testCategory_3", "test-category-3", "test-url-3", 0, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryServiceImpl).build();
    }

    @Test
    void getCategory_success() {
        Mockito.when(categoryRepository.findById(category_1.getId())).thenReturn(Optional.of(category_1));
        CommonResponse<CategoryOfTree> response = categoryServiceImpl.getCategory(category_1.getId());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getCategory_failure() {
        Mockito.when(categoryRepository.findById(category_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<CategoryOfTree> response = categoryServiceImpl.getCategory(category_1.getId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void getCategoryTree_success() {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        CommonResponse<List<CategoryOfTree>> response = categoryServiceImpl.getCategoryTree();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getCategoryTree_failure() {
        Mockito.when(categoryRepository.findAll()).thenThrow(new NullPointerException());
        CommonResponse<List<CategoryOfTree>> response = categoryServiceImpl.getCategoryTree();
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postCategory_success() {
        Mockito.when(categoryRepository.save(category_1)).thenReturn(category_1);
        CommonResponse<CategoryOfTree> response = categoryServiceImpl.postCategory(category_1);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void postCategory_failure() {
        Mockito.when(categoryRepository.save(category_1)).thenThrow(new NullPointerException());
        CommonResponse<CategoryOfTree> response = categoryServiceImpl.postCategory(category_1);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postCategoryTree_success() {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        Mockito.when(categoryRepository.saveAll(categories)).thenReturn(categories);
        CommonResponse<List<CategoryOfTree>> response = categoryServiceImpl.postCategoryTree(categories);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void postCategoryTree_failure() {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        Mockito.when(categoryRepository.saveAll(categories)).thenThrow(new NullPointerException());
        CommonResponse<List<CategoryOfTree>> response = categoryServiceImpl.postCategoryTree(categories);
        assertEquals(404, response.getStatusCode());
    }
}
