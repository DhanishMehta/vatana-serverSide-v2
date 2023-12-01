package com.capstone.grocery.service;

import java.util.List;

import com.capstone.grocery.model.CategoryOfTree;
import com.capstone.grocery.response.CommonResponse;

public interface CategoryService {

    public CommonResponse<CategoryOfTree> getCategory(Integer id);

    public CommonResponse<CategoryOfTree> postCategory(CategoryOfTree category);

    public CommonResponse<List<CategoryOfTree>> getCategoryTree();

    public CommonResponse<List<CategoryOfTree>> postCategoryTree(List<CategoryOfTree> categoryList);
}
