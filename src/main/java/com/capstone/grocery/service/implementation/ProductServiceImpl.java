package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.repository.ProductRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.response.Pagination;
import com.capstone.grocery.service.ProductService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public CommonResponse<Page<Product>> getAllProducts(Integer limit, Integer page) {
        try {
            limit = limit != null ? limit : 20;
            page = page != null ? page : 1;

            Pageable customPage = PageRequest.of(page - 1, limit);
            Page<Product> products = productRepository.findAll(customPage);
            Long total = productRepository.count();
            Long totalPages = total / limit;
            return Utility.getCommonResponse(200, true, "Products List", new Pagination(page, limit, totalPages, total),
                    products);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Products Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<Product> getProductById(String productId) {
        try {
            Product product = productRepository.findById(productId).get();
            return Utility.getCommonResponse(200, true, "Product Found", null, product);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<Product> addProduct(Product product) {
        try {
            productRepository.save(product);
            return Utility.getCommonResponse(200, true, "Product Added Successfully", null, product);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Addition Failed", null, null);
        }
    }

    @Override
    public CommonResponse<List<Product>> addProductInBulk(List<Product> products) {
        try {
            products.forEach((product) -> productRepository.save(product));
            return Utility.getCommonResponse(200, true, "Product Added Successfully", null, products);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Addition Failed", null, null);
        }
    }

    @Override
    public CommonResponse<Product> updateProduct(String productId, Product product) {
        try {
            Product oldProduct = productRepository.findById(productId).get();
            oldProduct.setAllAttributes(product);
            productRepository.save(oldProduct);
            return Utility.getCommonResponse(200, true, "Product Updated", null, oldProduct);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<Product> deleteProduct(String productId) {
        try {
            Product product = productRepository.findById(productId).get();
            productRepository.delete(product);
            return Utility.getCommonResponse(200, true, "Products Deleted", null, product);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<List<Product>> deleteAllProduct() {
        try {
            productRepository.deleteAll();
            return Utility.getCommonResponse(200, true, "ALL Products Deleted", null, null);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Product Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<Page<Product>> findProductsBySearchParams(String searchParams, Integer limit, Integer page,
            String category, String sortBy) {
        try {
            limit = limit != null ? limit : 20;
            page = page != null ? page : 1;
            category = category != null ? category : "";

            Sort customSort = Sort.unsorted();
            if (sortBy == "price-htl") {
            customSort = Sort.by("pricing.discount.mrp").descending();
            } else if (sortBy == "price-lth") {
            customSort = Sort.by("pricing.discount.mrp").ascending();
            } else if (sortBy == "rating-htl") {
            customSort = Sort.by("rating_info.avg_rating").descending();
            } else if (sortBy == "rating-lth") {
            customSort = Sort.by("rating_info.avg_rating").ascending();
            }
            Pageable customPage = PageRequest.of(page - 1, limit, customSort);
            if (category == null || category == "") {
                Page<Product> products = productRepository.findBySearchParam(searchParams, customPage);
                Long total = Long.valueOf(productRepository.findBySearchParam(searchParams).size());
                Long totalPages = total / limit;
                return Utility.getCommonResponse(200, true, "Filtered Products",
                new Pagination(page, limit, totalPages, total), products);
            } else {
                Page<Product> products = productRepository.findBySearchParamOnCategory(searchParams, category, customPage);
                Long total = Long.valueOf(productRepository.findBySearchParam(searchParams).size());
                Long totalPages = total / limit;
                return Utility.getCommonResponse(200, true, "Filtered Products",
                        new Pagination(page, limit, totalPages, total), products);
            }

        } catch (Exception exc) {
            return Utility.getCommonResponse(404, true, "Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<List<Product>> findProductByPriceRange(int lowerPrice, int higherPrice, Integer limit,
            Integer page) {
        try {
            limit = limit != null ? limit : 20;
            page = page != null ? page : 1;
            Pageable customPage = PageRequest.of(page - 1, limit);

            List<Product> products = productRepository.findByPriceRange(lowerPrice, higherPrice, customPage);

            Long total = Long.valueOf(productRepository.findByPriceRange(lowerPrice, higherPrice).size());
            Long totalPages = total / limit;

            return Utility.getCommonResponse(200, true, "Products List between given range",
                    new Pagination(page, limit, totalPages, total), products);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Products Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<List<Product>> sortProductsByPrice(boolean isAscending) {
        try {
            List<Product> products = new ArrayList<>();
            if (isAscending) {
                products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productPrice"));
            } else {
                products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "productPrice"));
            }
            // TODO
            return Utility.getCommonResponse(200, true, "Products List sorted in Ascending=" + isAscending, null,
                    products);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "Product Not Found", null, null);
        }
    }

    @Override
    public CommonResponse<List<Product>> findProductsByCategory(String llc, String mlc, String tlc) {
        try {
            String category = llc != null ? llc : mlc != null ? mlc : tlc != null ? tlc : "";
            List<Product> products = productRepository.findByCategory(category);
            // TODO
            return Utility.getCommonResponse(200, true, "TLC Items: " + category, null, products);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "Item not found", null, null);
        }
    }

}