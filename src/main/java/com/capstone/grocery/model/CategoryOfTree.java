package com.capstone.grocery.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("productCategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryOfTree {
    @Id
    Integer id;
    String name;
    String slug;
    String url;
    int level;
    List<Banner> bannersList;
    List<CategoryOfTree> children;
    String dest_type;
    String dest_slug;
    String type;
}
