package com.capstone.grocery.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    private String id;
    private String couponCode;
    private Date startDate;
    private Date endDate;
    private double redemption;
    private boolean isValid;
}