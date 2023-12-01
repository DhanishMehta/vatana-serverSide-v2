package com.capstone.grocery.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capstone.grocery.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);
    
}
