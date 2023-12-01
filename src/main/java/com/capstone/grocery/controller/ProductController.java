package com.capstone.grocery.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.product.Product;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.ProductService;

import lombok.RequiredArgsConstructor;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public CommonResponse<Page<Product>> getAllProducts(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page, @RequestParam(required = false) String fields) {
        return productService.getAllProducts(limit, page);
    }

    @GetMapping("/{productId}")
    public CommonResponse<Product> getProductById(@PathVariable String productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public CommonResponse<Product> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping("/bulk")
    public CommonResponse<List<Product>> addProductInBulk(@RequestBody List<Product> products) {
        return productService.addProductInBulk(products);
    }

    @PutMapping("{productId}")
    public CommonResponse<Product> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("{productId}")
    public CommonResponse<Product> deleteProduct(@PathVariable String productId) {
        return productService.deleteProduct(productId);
    }

    @DeleteMapping("/all")
    public CommonResponse<List<Product>> deleteAllProduct() {
        return productService.deleteAllProduct();
    }

    @GetMapping("/search")
    public CommonResponse<Page<Product>> findProductsBySearchParams(@RequestParam String q, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page, @RequestParam(required = false) String category, @RequestParam(required = false) String sortBy) {
        System.out.println("Inside /search get mapping");
        System.out.println(q);
        System.out.println(limit);
        System.out.println(page);
        System.out.println(category);
        System.out.println(sortBy);
        return productService.findProductsBySearchParams(q, limit, page, category, sortBy);
    }
    
    @GetMapping("/category")
    public CommonResponse<List<Product>> getProductsByCategory(@RequestParam(required = false) String llc, @RequestParam(required = false) String mlc, @RequestParam(required = false) String tlc) {
        return productService.findProductsByCategory(llc, mlc, tlc);
    }

    // @GetMapping("/category?mlc={midLevelCategorySlug}")
    // public CommonResponse<List<Product>> getProductsByMidlevelCategory(@PathVariable String midLevelCategorySlug) {
    //     return productService.findProductsByMidLevelCategory(midLevelCategorySlug);
    // }

    // @GetMapping("/category?tlc={topLevelCategorySlug}")
    // public CommonResponse<List<Product>> getProductsByToplevelCategory(@PathVariable String topLevelCategorySlug) {
    //     return productService.findProductsByLowLevelCategory(topLevelCategorySlug);
    // }


}
