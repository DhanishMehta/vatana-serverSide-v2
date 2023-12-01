package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    private String avail_status;
    private boolean display_mrp;
    private boolean display_sp;
    private boolean not_for_sale;
    private String button;
    private boolean show_express;
}
