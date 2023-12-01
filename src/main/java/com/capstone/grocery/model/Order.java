package com.capstone.grocery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("orders")
@Data
@Builder
public class Order {
    @Id
    String orderId;
    String userId;
    Billing billingDetails;
    Address address;
    PaymentDetails paymentDetails;
    CheckoutItem[] items;
    CheckoutPrice pricing;
    OrderStatus orderStatus;
}
