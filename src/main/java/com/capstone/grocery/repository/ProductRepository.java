package com.capstone.grocery.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.capstone.grocery.model.product.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{$project: { 'discount.mrp_int': { $toInt: '$pricing.discount.mrp' } }},{$match: { 'discount.mrp_int': { $gt: ?0, $lt: ?1 } }}")
    List<Product> findByPriceRange(int lowerPrice, int higherPrice);

    @Query("{ $or: [{ id: { $regex: ?0, $options: 'i' } },{ desc: { $regex: ?0, $options: 'i' } },{ sku_max_quantity: { $regex: ?0, $options: 'i' } },{ pack_desc: { $regex: ?0, $options: 'i' } },{ weight: { $regex: ?0, $options: 'i' } },{ absolute_url: { $regex: ?0, $options: 'i' } },{ usp: { $regex: ?0, $options: 'i' } },{ variableWeight: { $regex: ?0, $options: 'i' } },{ 'category.tlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.mlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.llc_slug': { $regex: ?0, $options: 'i' } },]}")
    Page<Product> findBySearchParam(String searchParam, Pageable customPage);
    
    @Query("{ $and: [ {$or: [{'category.tlc_slug': ?1}, {'category.mlc_slug': ?1}, {'category.llc_slug': ?1}]}, {$or: [{ id: { $regex: ?0, $options: 'i' } },{ desc: { $regex: ?0, $options: 'i' } },{ sku_max_quantity: { $regex: ?0, $options: 'i' } },{ pack_desc: { $regex: ?0, $options: 'i' } },{ weight: { $regex: ?0, $options: 'i' } },{ absolute_url: { $regex: ?0, $options: 'i' } },{ usp: { $regex: ?0, $options: 'i' } },{ variableWeight: { $regex: ?0, $options: 'i' } },{ 'category.tlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.mlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.llc_slug': { $regex: ?0, $options: 'i' } }]} ] }")
    Page<Product> findBySearchParamOnCategory(String searchParam, String category, Pageable customPage);
    
    @Query("{ $or: [{ id: { $regex: ?0, $options: 'i' } },{ desc: { $regex: ?0, $options: 'i' } },{ sku_max_quantity: { $regex: ?0, $options: 'i' } },{ pack_desc: { $regex: ?0, $options: 'i' } },{ weight: { $regex: ?0, $options: 'i' } },{ absolute_url: { $regex: ?0, $options: 'i' } },{ usp: { $regex: ?0, $options: 'i' } },{ variableWeight: { $regex: ?0, $options: 'i' } },{ 'category.tlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.mlc_slug': { $regex: ?0, $options: 'i' } },{ 'category.llc_slug': { $regex: ?0, $options: 'i' } },]}")
    List<Product> findBySearchParam(String searchParam);

    @Query("{ $or: [{'category.tlc_slug': ?0}, {'category.mlc_slug': ?0}, {'category.llc_slug': ?0}]}")
    List<Product> findByCategory(String topLevelCategorySlug);

    @Query("{$project: { 'discount.mrp_int': { $toInt: '$pricing.discount.mrp' } }},{$match: { 'discount.mrp_int': { $gt: ?0, $lt: ?1 } }}")
    List<Product> findByPriceRange(int lowerPrice, int higherPrice, Pageable customPage);
}
