package com.capstone.grocery.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.CategoryOfTree;
import com.capstone.grocery.repository.CategoryRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class CategoryControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    CategoryOfTree category_1 = new CategoryOfTree(1, "testCategory_1", "test-category-1", "test-url-1", 0, null, null,
            null, null, null);
    CategoryOfTree category_2 = new CategoryOfTree(2, "testCategory_2", "test-category-2", "test-url-2", 0, null, null,
            null, null, null);
    CategoryOfTree category_3 = new CategoryOfTree(3, "testCategory_3", "test-category-3", "test-url-3", 0, null, null,
            null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getCategory_success() throws Exception {
        CommonResponse<CategoryOfTree> expectedResponse = new CommonResponse<CategoryOfTree>(200, true,
                "Category testing", null, category_1);

        Mockito.when(categoryService.getCategory(category_1.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/category/{id}", category_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(category_1.getId())));
    }

    @Test
    void getCategory_failure() throws Exception {
        CommonResponse<CategoryOfTree> expectedResponse = new CommonResponse<CategoryOfTree>(404, false,
                "Category testing", null, null);

        Mockito.when(categoryService.getCategory(category_1.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/category/{id}", category_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void getCategoryTree_success() throws Exception {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        CommonResponse<List<CategoryOfTree>> expectedResponse = new CommonResponse<List<CategoryOfTree>>(200, true,
                "Category testing", null, categories);

        Mockito.when(categoryService.getCategoryTree()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/category/tree")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", is(category_1.getId())));
    }

    @Test
    void getCategoryTree_failure() throws Exception {
        CommonResponse<List<CategoryOfTree>> expectedResponse = new CommonResponse<List<CategoryOfTree>>(404, false,
                "Category testing", null, null);

        Mockito.when(categoryService.getCategoryTree()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/category/tree")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void postCategory_success() throws Exception {
        CommonResponse<CategoryOfTree> expectedResponse = new CommonResponse<CategoryOfTree>(200, true,
                "Category testing", null, category_1);

        Mockito.when(categoryService.postCategory(category_1)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .content(objectWriter.writeValueAsString(category_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(category_1.getId())));
    }

    @Test
    void postCategory_failure() throws Exception {
        CommonResponse<CategoryOfTree> expectedResponse = new CommonResponse<CategoryOfTree>(404, false,
                "Category testing", null, null);

        Mockito.when(categoryService.postCategory(category_1)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .content(objectWriter.writeValueAsString(category_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void postCategoryTree_success() throws Exception {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        CommonResponse<List<CategoryOfTree>> expectedResponse = new CommonResponse<List<CategoryOfTree>>(200, true,
                "Category testing", null, categories);

        Mockito.when(categoryService.postCategoryTree(categories)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category/tree")
                .content(objectWriter.writeValueAsString(categories))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", is(category_1.getId())));
    }

    @Test
    void postCategoryTree_failure() throws Exception {
        List<CategoryOfTree> categories = new ArrayList<>(Arrays.asList(category_1, category_2, category_3));
        CommonResponse<List<CategoryOfTree>> expectedResponse = new CommonResponse<List<CategoryOfTree>>(404, false,
                "Category testing", null, null);

        Mockito.when(categoryService.postCategoryTree(categories)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category/tree")
                .content(objectWriter.writeValueAsString(categories))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }
}
