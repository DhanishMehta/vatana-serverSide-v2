package com.capstone.grocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.Coupon;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;


    @GetMapping
    public CommonResponse<List<Coupon>> getAllCoupons() {
        return this.couponService.getAllCoupons();
    }

    @GetMapping("/{id}")
    public CommonResponse<Coupon> getCouponById(@PathVariable String id) {
        return this.couponService.getCouponById(id);
    }

    @PostMapping
    public CommonResponse<Coupon> postCoupon(@RequestBody Coupon coupon) {
        return this.couponService.postCoupon(coupon);
    }

    @PutMapping
    public CommonResponse<Coupon> updateCoupon(@RequestBody Coupon coupon) {
        return this.couponService.updateCoupon(coupon);
    }

    @GetMapping("/validate/{couponCode}")
    public CommonResponse<Coupon> validateCoupon(@PathVariable String couponCode) {
        return this.couponService.validateCoupon(couponCode);
    }
}
