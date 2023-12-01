package com.capstone.grocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.Review;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.ReviewService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public CommonResponse<List<Review>> getAllReviews(@RequestParam(required = false) String user,
            @RequestParam(required = false) String product) {
        if (user != null) {
            return this.reviewService.getReviewByUserId(user);
        } else if (product != null) {
            return this.reviewService.getReviewByProductId(product);
        } else
            return this.reviewService.getAllReviews();
    }

    @GetMapping("/{reviewId}")
    public CommonResponse<Review> getReview(@PathVariable String reviewId) {
        return this.reviewService.getReviewById(reviewId);
    }

    @PostMapping
    public CommonResponse<Review> postReview(@RequestBody Review newReview) {
        return this.reviewService.postReview(newReview);
    }
}
