package com.capstone.grocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.Order;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping
    public CommonResponse<List<Order>> getAllOrders(){
        return this.orderService.getAllOrders();
    }
    
    @GetMapping("/{orderId}")
    public CommonResponse<Order> getOrderById(@PathVariable String orderId){
        System.out.println("Get order by id");
        return this.orderService.getOrderById(orderId);
    }

    @GetMapping("/user")
    public CommonResponse<List<Order>> getOrderByUserId(@RequestParam String id){
        System.out.println("Get order by id");
        return this.orderService.getOrderByUserId(id);
    }

    @PostMapping
    public CommonResponse<Order> postOrder(@RequestBody Order newOrder){
        return this.orderService.postOrder(newOrder);
    }
}
