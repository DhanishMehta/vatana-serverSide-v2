package com.capstone.grocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.CategoryOfTree;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CommonResponse<CategoryOfTree> getCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

    @PostMapping
    public CommonResponse<CategoryOfTree> postCategory(@RequestBody CategoryOfTree category){
        return categoryService.postCategory(category);
    }

    @GetMapping("/tree")
    public CommonResponse<List<CategoryOfTree>> getCategoryTree(){
        return categoryService.getCategoryTree();
    }

    @PostMapping("/tree")
    public CommonResponse<List<CategoryOfTree>> postCategoryTree(@RequestBody List<CategoryOfTree> categoryList){
        return categoryService.postCategoryTree(categoryList);
    }
}
