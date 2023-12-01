package com.capstone.grocery.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    
    @Id
    private String addressId;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressState;
    private String addressCity;
    private String addressPincode;
    private String addressLandmark;
    private String addressGeoHash;
}
