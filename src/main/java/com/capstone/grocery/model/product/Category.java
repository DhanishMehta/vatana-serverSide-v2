package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Category {
private String tlc_name;
private String tlc_slug;
private String mlc_name;
private String mlc_slug;
private int mlc_id;
private String llc_name;
private String llc_slug;
private int llc_id;
}
