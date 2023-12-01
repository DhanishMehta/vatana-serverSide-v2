package com.capstone.grocery.service;

import java.util.List;

import com.capstone.grocery.model.Order;
import com.capstone.grocery.response.CommonResponse;

public interface OrderService {
    public CommonResponse<List<Order>> getAllOrders();
    public CommonResponse<Order> getOrderById(String orderId);
    public CommonResponse<List<Order>> getOrderByUserId(String orderId);
    public CommonResponse<Order> postOrder(Order newOrder);
}
