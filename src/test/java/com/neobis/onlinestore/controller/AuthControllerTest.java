package com.neobis.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.Role;
import com.neobis.onlinestore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    private final String URL = "/api/auth";

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private WebApplicationContext webApplicationContext;
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public AuthControllerTest(ObjectMapper mapper, WebApplicationContext webApplicationContext, UserRepository userRepository) {
        this.mapper = mapper;
        this.webApplicationContext = webApplicationContext;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.userRepository = userRepository;
    }

    @Test
    void registration() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UserRequest.builder()
                                .password("regis_password").username("regis_username").build())))
                .andDo(print())
                .andExpect(content().string("User successfully registered!"))
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        if (userRepository.findByUsername("login_test").isEmpty()) {
            userRepository.save(User.builder()
                    .username("login_test")
                    .password(encoder.encode("password"))
                    .role(Role.USER).build());
        }
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL + "/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(LoginRequest.builder()
                                .password("password").username("login_test").build())))
                .andDo(print())
                .andExpect(status().isOk());
    }
}