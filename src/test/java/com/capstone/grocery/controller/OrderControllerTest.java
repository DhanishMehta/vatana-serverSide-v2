package com.capstone.grocery.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.Order;
import com.capstone.grocery.repository.OrderRepository;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class OrderControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    Order order_1 = new Order("testOrder_1", "testUser", null, null, null, null, null, null);
    Order order_2 = new Order("testOrder_2", "testUser", null, null, null, null, null, null);
    Order order_3 = new Order("testOrder_3", "testUser", null, null, null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1, order_2, order_3));
        CommonResponse<List<Order>> expectedResponse = new CommonResponse<List<Order>>(200, true, "test orders", null,
                orders);

        Mockito.when(orderService.getAllOrders()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[2].userId", is(order_3.getUserId())));
    }

    @Test
    void getOrderById_success() throws Exception {
        CommonResponse<Order> expectedResponse = new CommonResponse<Order>(200, true, "test orders", null, order_1);

        Mockito.when(orderService.getOrderById(order_1.getOrderId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders/{orderId}", order_1.getOrderId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.userId", is(order_1.getUserId())));
    }

    @Test
    void getOrderById_failure() throws Exception {
        CommonResponse<Order> expectedResponse = new CommonResponse<Order>(404, false, "test orders", null, null);

        Mockito.when(orderService.getOrderById("randomOrder")).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders/{orderId}", "randomOrder")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void getOrderByUserId() throws Exception {
        List<Order> orders = new ArrayList<>(Arrays.asList(order_1));
        CommonResponse<List<Order>> expectedResponse = new CommonResponse<List<Order>>(200, true, "test orders", null,
                orders);

        Mockito.when(orderService.getOrderByUserId(order_1.getUserId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders/user")
                .param("id", order_1.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].userId", is(order_1.getUserId())))
                .andExpect(jsonPath("$.data[0].orderId", is(order_1.getOrderId())));
    }

    @Test
    void postOrder() throws Exception {
        CommonResponse<Order> expectedResponse = new CommonResponse<Order>(200, true, "test orders", null, order_2);

        Mockito.when(orderService.postOrder(order_2)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/orders")
            .content(objectWriter.writeValueAsString(order_2))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.data.orderId", is(order_2.getOrderId())))
            .andExpect(jsonPath("$.data.userId", is(order_2.getUserId())))

        ;
    }
}
