package com.capstone.grocery.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    private String campaign_type_slug;
    private boolean offer_logo_web;
    private String arrow_image;
    private String offer_available;
    private String campaign_type;
    private String offer_logo;
    private String offer_logo_big;
    private String text_color;
    private String offer_logo_small;
    private String offer_ent_txt;
    private String offer_entry_text;
}
