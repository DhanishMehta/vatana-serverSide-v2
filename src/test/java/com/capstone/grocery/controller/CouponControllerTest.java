package com.capstone.grocery.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
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

import com.capstone.grocery.model.Coupon;
import com.capstone.grocery.repository.CouponRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class CouponControllerTest {

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private CouponRepository couponRepository;

	@Mock
	private CouponService couponService;

	@InjectMocks
	private CouponController couponController;

	Coupon coupon_1 = new Coupon("test1", "test1", new Date(), new Date(), 0.00, false);
	Coupon coupon_2 = new Coupon("test2", "test2", new Date(), new Date(), 0.00, false);
	Coupon coupon_3 = new Coupon("test3", "test3", new Date(), new Date(), 0.00, false);

    @BeforeEach
    void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
	}

    @Test
    void getAllCoupons_success() throws Exception {
		List<Coupon> coupons = new ArrayList<>(Arrays.asList(coupon_1, coupon_2, coupon_3));
		CommonResponse<List<Coupon>> expectedResponse = new CommonResponse<List<Coupon>>(200, true, "test Coupons",
				null, coupons);
		Mockito.when(couponService.getAllCoupons()).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/coupons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(3)))
				.andExpect(jsonPath("$.data[2].couponCode", is("test3")));
	}

    @Test
    void getCouponById_success() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(200, true, "test Coupons", null, coupon_1);
		Mockito.when(couponService.getCouponById(coupon_1.getId())).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/coupons/{id}", coupon_1.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.couponCode", is("test1")));
	}

    @Test
    void getCouponById_failure() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(404, false, "test Coupons", null, null);
		Mockito.when(couponService.getCouponById("randomId")).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/coupons/{id}", "randomId")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(false)));
	}

    @Test
    void postCoupon() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(200, true, "test Coupons", null, coupon_1);
		Mockito.when(couponService.postCoupon(coupon_1)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.post("/coupons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectWriter.writeValueAsString(coupon_1))
				)
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.data.couponCode", is(coupon_1.getCouponCode())))
		;
	}

    @Test
    void updateCoupon() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(200, true, "test Coupons", null, coupon_1);
		Mockito.when(couponService.updateCoupon(coupon_1)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.put("/coupons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectWriter.writeValueAsString(coupon_1))
				)
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.data.couponCode", is(coupon_1.getCouponCode())))
		;
	}

    @Test
    void validateCoupon_success() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(200, true, "test Coupons", null, coupon_1);
		Mockito.when(couponService.validateCoupon("test1")).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/coupons/validate/{couponCode}", "test1")
				.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.data.couponCode", is(coupon_1.getCouponCode())))
		;
	}

    @Test
    void validateCoupon_failure() throws Exception {
		CommonResponse<Coupon> expectedResponse = new CommonResponse<Coupon>(404, false, "test Coupons", null, null);
		Mockito.when(couponService.validateCoupon("randomCoupon")).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/coupons/validate/{couponCode}", "randomCoupon")
				.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.success", is(false)))
		;
	}
}
