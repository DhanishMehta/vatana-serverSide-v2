package com.capstone.grocery.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.grocery.model.CategoryOfTree;
import com.capstone.grocery.repository.CategoryRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CategoryService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CommonResponse<CategoryOfTree> getCategory(Integer id) {
        try {
            CategoryOfTree category = categoryRepository.findById(id).get();
            return Utility.getCommonResponse(200, true, "Category found", null, category);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Category NOT FOUND", null, null);
        }
    }

    @Override
    public CommonResponse<CategoryOfTree> postCategory(CategoryOfTree category) {
        try {

            categoryRepository.save(category);
            return Utility.getCommonResponse(200, true, "Category found", null, category);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Category NOT FOUND", null, null);
        }
    }

    @Override
    public CommonResponse<List<CategoryOfTree>> getCategoryTree() {
        try {

            List<CategoryOfTree> categoryList = categoryRepository.findAll();
            return Utility.getCommonResponse(200, true, "Category found", null, categoryList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Category NOT FOUND", null, null);
        }
    }

    @Override
    public CommonResponse<List<CategoryOfTree>> postCategoryTree(List<CategoryOfTree> categoryList) {
        try {

            categoryRepository.saveAll(categoryList);
            return Utility.getCommonResponse(200, true, "Category found", null, categoryList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Category NOT FOUND", null, null);
        }
    }
}
