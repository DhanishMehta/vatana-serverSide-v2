package com.capstone.grocery.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capstone.grocery.model.Coupon;

public interface CouponRepository extends MongoRepository<Coupon, String> {

    Optional<Coupon> findByCouponCode(String couponCode);
    
}
