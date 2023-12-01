package com.capstone.grocery.model;

import com.capstone.grocery.model.product.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Product cartItemProduct;
    private int cartItemQuantity;
}
