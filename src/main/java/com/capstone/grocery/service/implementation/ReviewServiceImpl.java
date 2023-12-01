package com.capstone.grocery.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.grocery.model.Review;
import com.capstone.grocery.repository.ReviewRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.ReviewService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;

    @Override
    public CommonResponse<List<Review>> getAllReviews() {
        try {
            List<Review> reviewList = this.reviewRepository.findAll();
            return Utility.getCommonResponse(200, true, "All reviews", null, reviewList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to find Reviews", null, null);
        }
    }

    @Override
    public CommonResponse<Review> getReviewById(String reviewId) {
        try {
            Review review = this.reviewRepository.findById(reviewId).get();
            return Utility.getCommonResponse(200, true, "Review found", null, review);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to find Reviews", null, null);
        }
    }

    @Override
    public CommonResponse<List<Review>> getReviewByUserId(String userId) {
        try {
            List<Review> reviewList = this.reviewRepository.findByUserId(userId);
            return Utility.getCommonResponse(200, true, "All reviews", null, reviewList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to find Reviews", null, null);
        }
    }

    @Override
    public CommonResponse<List<Review>> getReviewByProductId(String productId) {
        try {
            List<Review> reviewList = this.reviewRepository.findByProductId(productId);
            return Utility.getCommonResponse(200, true, "All reviews", null, reviewList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to find Reviews", null, null);
        }
    }

    @Override
    public CommonResponse<Review> postReview(Review newReview) {
        try {
            this.reviewRepository.save(newReview);
            return Utility.getCommonResponse(200, true, "All reviews", null, newReview);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Unable to find Reviews", null, null);
        }
    }
}
