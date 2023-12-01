package com.capstone.grocery.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capstone.grocery.model.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{

    List<Review> findByUserId(String userId);

    List<Review> findByProductId(String productId);

    
}
