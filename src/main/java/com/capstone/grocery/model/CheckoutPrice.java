package com.capstone.grocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutPrice {
    double orderTotal;
    double discount;
    double delivery;
    double GST;
    double grandTotal;
}
