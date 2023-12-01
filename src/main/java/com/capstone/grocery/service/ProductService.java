package com.capstone.grocery.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.response.CommonResponse;

public interface ProductService {

    public CommonResponse<Page<Product>> getAllProducts(Integer limit, Integer page);

    public CommonResponse<Product> getProductById(String productId);

    public CommonResponse<Product> addProduct(Product product);

    public CommonResponse<List<Product>> addProductInBulk(List<Product> products);

    public CommonResponse<Product> updateProduct(String productId, Product product);

    public CommonResponse<Product> deleteProduct(String productId);

    public CommonResponse<List<Product>> deleteAllProduct();

    public CommonResponse<Page<Product>> findProductsBySearchParams(String searchParams, Integer limit, Integer page, String category, String sortBy);

    public CommonResponse<List<Product>> findProductByPriceRange(int lowerPrice, int higherPrice, Integer limit,
            Integer page);

    public CommonResponse<List<Product>> sortProductsByPrice(boolean isAscending);

    public CommonResponse<List<Product>> findProductsByCategory(String llc, String mlc, String tlc);
}
