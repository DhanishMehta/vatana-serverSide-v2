package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentInfo {
    private int id;
    private int parent_product_id;
    private int child_product_id;
    private int order;
    private int parent_id;
    private int child_id;
    private int is_default;
    private int type;
    private int created_by_id;
    private int updated_by_id;
    private String created_on;
    private String updated_on;
}
