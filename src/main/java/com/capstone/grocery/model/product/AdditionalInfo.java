package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfo {
    private boolean other_app_pd;
    private boolean per_unit_pd_page;
    private boolean other_app_listing;
    private boolean per_unit_listing_page;
    private boolean per_unit_pack_selector;
}