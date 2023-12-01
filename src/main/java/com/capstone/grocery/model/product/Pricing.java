package com.capstone.grocery.model.product;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Pricing {
    private Discount discount;
    private Offer offer;
}
