package com.capstone.grocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItem {
    String productName;
    String productImage;
    String price;
    String quantity;
}
