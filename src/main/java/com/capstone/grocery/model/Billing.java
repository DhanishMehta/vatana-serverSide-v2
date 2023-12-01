package com.capstone.grocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
    String firstName;
    String lastName;
    String emailAddress;
    String phoneNo;
    String companyName;
    String companyAddress;
}
