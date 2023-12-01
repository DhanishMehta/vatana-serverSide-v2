package com.capstone.grocery.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("reviews")
public class Review {
    @Id
    private String reviewId;
    private String productId;
    private String userId;
    private String userName;
    private String reviewTitle;
    private String reviewMessage;
    private double reviewRating;
    private Date reviewTime;
}