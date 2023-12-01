package com.capstone.grocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    String paymentMethod;
    String transactionId;
    boolean success;
}
