package com.capstone.grocery.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.Review;
import com.capstone.grocery.repository.ReviewRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class ReviewControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    Review review_1 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());
    Review review_2 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());
    Review review_3 = new Review("testReview1", "testProduct", "testUser", "testUserName", "testTitle", "testMessage",
            3.00, new Date());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void getAllReviews() throws Exception {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1, review_2, review_3));
        CommonResponse<List<Review>> expectedResponse = new CommonResponse<List<Review>>(200, true, "test reviews",
                null, reviews);

        Mockito.when(reviewService.getAllReviews()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].reviewId", is(review_1.getReviewId())))
                .andExpect(jsonPath("$.data[0].userId", is(review_1.getUserId())))

        ;
    }

    @Test
    void getAllReviews_withUserIdAsParam() throws Exception {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_1));
        CommonResponse<List<Review>> expectedResponse = new CommonResponse<List<Review>>(200, true, "test reviews",
                null, reviews);

        Mockito.when(reviewService.getReviewByUserId(review_1.getUserId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reviews")
                .param("user",review_1.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].reviewId", is(review_1.getReviewId())))
                .andExpect(jsonPath("$.data[0].userId", is(review_1.getUserId())))

        ;
    }

    @Test
    void getAllReviews_withProductIdAsParam() throws Exception {
        List<Review> reviews = new ArrayList<>(Arrays.asList(review_2));
        CommonResponse<List<Review>> expectedResponse = new CommonResponse<List<Review>>(200, true, "test reviews",
                null, reviews);

        Mockito.when(reviewService.getReviewByProductId(review_2.getProductId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reviews")
                .param("product",review_2.getProductId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].reviewId", is(review_2.getReviewId())))
                .andExpect(jsonPath("$.data[0].userId", is(review_2.getUserId())))

        ;
    }



    @Test
    void getReview_success() throws Exception {
        CommonResponse<Review> expectedResponse = new CommonResponse<Review>(200, true, "test reviews", null, review_1);

        Mockito.when(reviewService.getReviewById(review_1.getReviewId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reviews/{reviewId}", review_1.getReviewId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.reviewId", is(review_1.getReviewId())))
                .andExpect(jsonPath("$.data.userId", is(review_1.getUserId())))

        ;
    }

    @Test
    void getReview_failure() throws Exception {
        CommonResponse<Review> expectedResponse = new CommonResponse<Review>(404, false, "test reviews", null, null);

        Mockito.when(reviewService.getReviewById("randomReview")).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reviews/{reviewId}", "randomReview")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void postReview() throws Exception {
        CommonResponse<Review> expectedResponse = new CommonResponse<Review>(200, true, "test reviews", null, review_2);

        Mockito.when(reviewService.postReview(review_2)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/reviews")
                .content(objectWriter.writeValueAsString(review_2))
                .contentType(MediaType.APPLICATION_JSON))
                // .andExpect(jsonPath("$.success", is(true)))
                // .andExpect(jsonPath("$.data.reviewId", is(review_1.getReviewId())))
                // .andExpect(jsonPath("$.data.userId", is(review_1.getUserId())))

        ;
    }
}
