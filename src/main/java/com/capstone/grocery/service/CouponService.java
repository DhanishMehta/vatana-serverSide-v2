package com.capstone.grocery.service;

import java.util.List;

import com.capstone.grocery.model.Coupon;
import com.capstone.grocery.response.CommonResponse;

public interface CouponService {
    
    public CommonResponse<List<Coupon>> getAllCoupons();
    public CommonResponse<Coupon> getCouponById(String id);
    public CommonResponse<Coupon> postCoupon(Coupon coupon);
    public CommonResponse<Coupon> updateCoupon(Coupon coupon);
    public CommonResponse<Coupon> validateCoupon(String couponCode);
}
