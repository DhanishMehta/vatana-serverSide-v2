package com.capstone.grocery.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.Review;
import com.capstone.grocery.repository.ReviewRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ReviewServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    Review review_1 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());
    Review review_2 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());
    Review review_3 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewServiceImpl).build();
    }

    @Test
    void getAllReviews_success() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findAll()).thenReturn(reviews);
        CommonResponse<List<Review>> response = reviewServiceImpl.getAllReviews();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getAllReviews_failure() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findAll()).thenThrow(new NullPointerException());
        CommonResponse<List<Review>> response = reviewServiceImpl.getAllReviews();
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void getReviewById_success() {
        Mockito.when(reviewRepository.findById(review_1.getReviewId())).thenReturn(Optional.of(review_1));
        CommonResponse<Review> response = reviewServiceImpl.getReviewById(review_1.getReviewId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void getReviewById_failure() {
        Mockito.when(reviewRepository.findById(review_1.getReviewId())).thenThrow(new NullPointerException());
        CommonResponse<Review> response = reviewServiceImpl.getReviewById(review_1.getReviewId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void getReviewByUserId_success() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findByUserId(review_1.getUserId())).thenReturn(reviews);
        CommonResponse<List<Review>> response = reviewServiceImpl.getReviewByUserId(review_1.getUserId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void getReviewByUserId_failure() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findByUserId(review_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<List<Review>> response = reviewServiceImpl.getReviewByUserId(review_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void getReviewByProductId_success() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findByProductId(review_1.getProductId())).thenReturn(reviews);
        CommonResponse<List<Review>> response = reviewServiceImpl.getReviewByProductId(review_1.getProductId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void getReviewByProductId_failure() {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        Mockito.when(reviewRepository.findByProductId(review_1.getProductId())).thenThrow(new NullPointerException());
        CommonResponse<List<Review>> response = reviewServiceImpl.getReviewByProductId(review_1.getProductId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postReview_success() {
        Mockito.when(reviewRepository.save(review_1)).thenReturn(review_1);
        CommonResponse<Review> response = reviewServiceImpl.postReview(review_1);
        assertEquals(200, response.getStatusCode());
    }  
     
    @Test
    void postReview_failure() {
        Mockito.when(reviewRepository.save(review_1)).thenThrow(new NullPointerException());
        CommonResponse<Review> response = reviewServiceImpl.postReview(review_1);
        assertEquals(404, response.getStatusCode());
    }    
}
