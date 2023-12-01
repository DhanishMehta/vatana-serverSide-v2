package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingInfo {
    private String avg_rating;
    private int rating_count;
    private int review_count;
    private int sku_id;
    private int order_count;
    private int member_count;
}
