package com.capstone.grocery.model.product;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    private String id;
    private String desc;
    private String sku_max_quantity;
    private String pack_desc;
    private int sort_index_pos;
    private int cart_count;
    private boolean is_best_value;
    private String weight;
    private String absolute_url;
    private String usp;
    private Availability availability;
    private Pricing pricing;
    private List<Image> images;
    private String variableWeight;
    private Brand brand;
    private Category category;
    private List<Product> children;
    private RatingInfo rating_info;
    private AdditionalInfo additional_info;
    private ParentInfo parent_info;

    public void setAllAttributes(Product newProduct) {
        this.desc = newProduct.desc;
        this.sku_max_quantity = newProduct.sku_max_quantity;
        this.pack_desc = newProduct.pack_desc;
        this.sort_index_pos = newProduct.sort_index_pos;
        this.cart_count = newProduct.cart_count;
        this.is_best_value = newProduct.is_best_value;
        this.weight = newProduct.weight;
        this.absolute_url = newProduct.absolute_url;
        this.usp = newProduct.usp;
        this.availability = newProduct.availability;
        this.pricing = newProduct.pricing;
        this.images = newProduct.images;
        this.variableWeight = newProduct.variableWeight;
        this.brand = newProduct.brand;
        this.category = newProduct.category;
        this.children = newProduct.children;
        this.rating_info = newProduct.rating_info;
        this.additional_info = newProduct.additional_info;
        this.parent_info = newProduct.parent_info;
    }

    public static final String ALL_PRODUCT_FIELDS = "id,desc,sku_max_quantity,pack_desc,sort_index_pos,cart_count,is_best_value,weight,absolute_url,usp,availability,pricing,images,variableWeight,brand,category,children,rating_info,additional_info,parent_info";
}