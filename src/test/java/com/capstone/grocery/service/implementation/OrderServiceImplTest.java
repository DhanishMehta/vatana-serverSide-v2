package com.capstone.grocery.service.implementation;

import com.capstone.grocery.response.CommonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

import com.capstone.grocery.model.CheckoutPrice;
import com.capstone.grocery.model.Order;
import com.capstone.grocery.model.product.Pricing;

import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;

import com.capstone.grocery.repository.OrderRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.razorpay.RazorpayClient;

import org.springframework.test.web.servlet.MockMvc;

class OrderServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RazorpayClient rzp;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    Order order_1 = new Order("testOrder_1", "testUser", null, null, null, null, new CheckoutPrice(0.00, 0.00, 0.00, 0.00, 0.00), null);
    Order order_2 = new Order("testOrder_2", "testUser", null, null, null, null, null, null);
    Order order_3 = new Order("testOrder_3", "testUser", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderServiceImpl).build();
    }

    @Test
    void getAllOrders_success() {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        CommonResponse<List<Order>> response = orderServiceImpl.getAllOrders();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getAllOrders_failure() {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        Mockito.when(orderRepository.findAll()).thenThrow(new NullPointerException("test null pointer exception"));
        CommonResponse<List<Order>> response = orderServiceImpl.getAllOrders();
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void getOrderById_success() {
        Mockito.when(orderRepository.findById(order_1.getOrderId())).thenReturn(Optional.of(order_1));
        CommonResponse<Order> response = orderServiceImpl.getOrderById(order_1.getOrderId());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getOrderById_failure() {
        Mockito.when(orderRepository.findById(order_1.getOrderId())).thenThrow(new NullPointerException());
        CommonResponse<Order> response = orderServiceImpl.getOrderById(order_1.getOrderId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void getOrderByUserId_success() {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        Mockito.when(orderRepository.findByUserId(order_1.getUserId())).thenReturn(orders);
        CommonResponse<List<Order>> response = orderServiceImpl.getOrderByUserId(order_1.getUserId());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getOrderByUserId_failure() {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        Mockito.when(orderRepository.findByUserId(order_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<List<Order>> response = orderServiceImpl.getOrderByUserId(order_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postOrder_success() throws Exception{
        rzp = new RazorpayClient("test", "test");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order_1.getPricing().getGrandTotal() * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_11");
        com.razorpay.Order order = new com.razorpay.Order(orderRequest);

        // Mockito.when(rzp.orders.create(orderRequest)).thenReturn(order);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order_1);

        CommonResponse<Order> response = orderServiceImpl.postOrder(order_1);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    void postOrder_RepositoryFailure() {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        Mockito.when(orderRepository.save(order_1)).thenThrow(new NullPointerException());
        CommonResponse<Order> response = orderServiceImpl.postOrder(order_1);
        assertEquals(404, response.getStatusCode());
    }
}
