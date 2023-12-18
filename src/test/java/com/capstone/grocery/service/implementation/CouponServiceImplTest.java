package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.Coupon;
import com.capstone.grocery.repository.CouponRepository;
import com.capstone.grocery.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class CouponServiceImplTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponServiceImpl;

    Coupon coupon_1 = new Coupon("test1", "test1", new Date(), new Date(), 0.00, false);
	Coupon coupon_2 = new Coupon("test2", "test2", new Date(), new Date(), 0.00, false);
	Coupon coupon_3 = new Coupon("test3", "test3", new Date(), new Date(), 0.00, false);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(couponServiceImpl).build();
    }

    @Test
    void getAllCoupons_success() {
        List<Coupon> coupons = new ArrayList<>(Arrays.asList(coupon_1, coupon_2, coupon_3));
        Mockito.when(couponRepository.findAll()).thenReturn(coupons);
        CommonResponse<List<Coupon>> response = couponServiceImpl.getAllCoupons();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getAllCoupons_failure() {
        Mockito.when(couponRepository.findAll()).thenThrow(new NullPointerException());
        CommonResponse<List<Coupon>> response = couponServiceImpl.getAllCoupons();
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void getCouponById_success() {
        Mockito.when(couponRepository.findById(coupon_1.getId())).thenReturn(Optional.of(coupon_1));
        CommonResponse<Coupon> response = couponServiceImpl.getCouponById(coupon_1.getId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void getCouponById_failure() {
        Mockito.when(couponRepository.findById(coupon_1.getId())).thenThrow(new NullPointerException());
        CommonResponse<Coupon> response = couponServiceImpl.getCouponById(coupon_1.getId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postCoupon_success() {
        Mockito.when(couponRepository.save(coupon_1)).thenReturn(coupon_1);
        CommonResponse<Coupon> response = couponServiceImpl.postCoupon(coupon_1);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void postCoupon_failure() {
        Mockito.when(couponRepository.save(coupon_1)).thenThrow(new NullPointerException());
        CommonResponse<Coupon> response = couponServiceImpl.postCoupon(coupon_1);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void updateCoupon_success() {
        Mockito.when(couponRepository.save(coupon_1)).thenReturn(coupon_1);
        CommonResponse<Coupon> response = couponServiceImpl.updateCoupon(coupon_1);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void updateCoupon_failure() {
        Mockito.when(couponRepository.save(coupon_1)).thenThrow(new NullPointerException());
        CommonResponse<Coupon> response = couponServiceImpl.updateCoupon(coupon_1);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void validateCoupon_success() {
        Mockito.when(couponRepository.findByCouponCode(coupon_1.getCouponCode())).thenReturn(Optional.of(coupon_1));
        CommonResponse<Coupon> response = couponServiceImpl.validateCoupon(coupon_1.getCouponCode());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void validateCoupon_failure() {
        Mockito.when(couponRepository.findByCouponCode(coupon_1.getCouponCode())).thenThrow(new NullPointerException());
        CommonResponse<Coupon> response = couponServiceImpl.validateCoupon(coupon_1.getCouponCode());
        assertEquals(404, response.getStatusCode());
    }
}
