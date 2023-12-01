package com.capstone.grocery.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.grocery.model.Coupon;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.CouponService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

import com.capstone.grocery.repository.CouponRepository;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public CommonResponse<List<Coupon>> getAllCoupons() {
        try {
            List<Coupon> couponList = this.couponRepository.findAll();
            return Utility.getCommonResponse(200, true, "All Coupons", null, couponList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Coupon Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Coupon> getCouponById(String id) {
        try {
            Coupon coupon = this.couponRepository.findById(id).get();
            return Utility.getCommonResponse(200, true, "Coupon found", null, coupon);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Coupon Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Coupon> postCoupon(Coupon coupon) {
        try {
            this.couponRepository.save(coupon);
            return Utility.getCommonResponse(200, true, "Coupon added", null, coupon);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Coupon Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Coupon> updateCoupon(Coupon coupon) {
        try {
            this.couponRepository.save(coupon);
            return Utility.getCommonResponse(200, true, "Coupon updated", null, coupon);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Coupon Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Coupon> validateCoupon(String couponCode) {
        try {
            Coupon coupon = this.couponRepository.findByCouponCode(couponCode).get();
            return Utility.getCommonResponse(200, true, "Validated", null, coupon);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Coupon Not found", null, null);
        }
    }

}
