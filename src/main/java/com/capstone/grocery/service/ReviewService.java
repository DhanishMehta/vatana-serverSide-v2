package com.capstone.grocery.service;

import java.util.List;

import com.capstone.grocery.model.Review;
import com.capstone.grocery.response.CommonResponse;

public interface ReviewService {
    
    public CommonResponse<List<Review>> getAllReviews();
    public CommonResponse<Review> getReviewById(String reviewId);
    public CommonResponse<List<Review>> getReviewByUserId(String userId);
    public CommonResponse<List<Review>> getReviewByProductId(String productId);
    public CommonResponse<Review> postReview(Review newReview);
}
