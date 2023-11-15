package com.neobis.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.onlinestore.dto.request.OrderRequest;
import com.neobis.onlinestore.entity.Order;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.OrderStage;
import com.neobis.onlinestore.repository.OrderRepository;
import com.neobis.onlinestore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    private final String URL = "/api/orders";

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private WebApplicationContext webApplicationContext;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    OrderControllerTest(MockMvc mockMvc, ObjectMapper mapper, WebApplicationContext webApplicationContext,
                        OrderRepository orderRepository, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.webApplicationContext = webApplicationContext;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void makeOrder() throws Exception {
        if (userRepository.findByUsername("user").isEmpty()) {
            userRepository.save(User.builder().username("user").build());
        }
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ArrayList<OrderRequest>()))
                        .param("address", "Moscow, Kremlin")
                        .param("type", "PICKUP"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAllOrders() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void getAllPersonalOrders() throws Exception {
        if (userRepository.findByUsername("user").isEmpty()) {
            userRepository.save(User.builder().username("user").build());
        }
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/my-orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}