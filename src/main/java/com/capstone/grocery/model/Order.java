package com.capstone.grocery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    private String orderId;
    private String userId;
    private Billing billingDetails;
    private Address address;
    private PaymentDetails paymentDetails;
    private CheckoutItem[] items;
    private CheckoutPrice pricing;
    private OrderStatus orderStatus;
}
