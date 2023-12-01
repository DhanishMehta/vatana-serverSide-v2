package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    private String mrp;
    private String d_text;
    private String d_avail;
    private String offer_entry_text;
    private String subscription_price;
    private String offer_available;
}