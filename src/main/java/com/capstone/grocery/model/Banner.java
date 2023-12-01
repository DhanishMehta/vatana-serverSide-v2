package com.capstone.grocery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    Integer id;
    String url;
    String campaign;
    String orientation;
    String image_name;
    String mobile_destination_type;
    String pwa_url;
    String banner_sec_id;
    String mobile_destination_slug;
    String banner_type;
}
