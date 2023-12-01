package com.capstone.grocery.service.implementation;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.capstone.grocery.model.Order;
import com.capstone.grocery.properties.Secrets;
import com.capstone.grocery.repository.OrderRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.OrderService;
import com.capstone.grocery.utility.Utility;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Secrets secrets;

    @Override
    public CommonResponse<List<Order>> getAllOrders() {
        try {
            List<Order> orderList = this.orderRepository.findAll();
            return Utility.getCommonResponse(200, true, "All orders", null, orderList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Orders Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Order> getOrderById(String orderId) {
        try {
            Order order = this.orderRepository.findById(orderId).get();
            return Utility.getCommonResponse(200, true, "Order Found", null, order);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Orders Not found", null, null);
        }
    }

    @Override
    public CommonResponse<List<Order>> getOrderByUserId(String userId) {
        try {
            List<Order> orderList = this.orderRepository.findByUserId(userId);
            return Utility.getCommonResponse(200, true, "Order Found", null, orderList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Orders Not found", null, null);
        }
    }

    @Override
    public CommonResponse<Order> postOrder(Order newOrder) {
        try {
            String orderId = null;
            
            // generate a razorpay order ID
            try {
                RazorpayClient razorpay = new RazorpayClient(secrets.getRzp_key_id(), secrets.getRzp_key_secret());
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", newOrder.getPricing().getGrandTotal() * 100);
                orderRequest.put("currency", secrets.getRzp_currency());
                orderRequest.put("receipt", "order_rcptid_11");

                com.razorpay.Order order = razorpay.orders.create(orderRequest);
                orderId = order.get("id");
            } catch (RazorpayException e) {
                System.out.println(e.getMessage());
            }

            // set the order id 
            newOrder.setOrderId(orderId);
            this.orderRepository.save(newOrder);
            return Utility.getCommonResponse(200, true, "Order Posted", null, newOrder);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Orders Not found", null, null);
        }
    }

}
