package com.neobis.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.UserInfo;
import com.neobis.onlinestore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    private final String URL = "/api/users";
    private final UserRequest userRequest = new UserRequest("username", "password", false);
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private WebApplicationContext webApplicationContext;

    @Mock
    private UserRepository userRepository;

    @Autowired
    UserControllerTest(ObjectMapper mapper, WebApplicationContext webApplicationContext, MockMvc mockMvc) {
        this.mapper = mapper;
        this.webApplicationContext = webApplicationContext;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"}, username = "checkGetAll")
    void get_all_users_isOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username_is_not_valid_at_all")
    void get_personal_info_isNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/info"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"}, username = "username")
    void get_personal_info() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/info"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"}, username = "username")
    void saveAdminUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username", authorities = {"USER"})
    void updateUserInfo() throws Exception {
        userRepository.save(User.builder().username("new_username").build());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new UserInfo())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username", authorities = {"USER"})
    void updateUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUserByUsername() throws Exception {
        if (userRepository.findByUsername("username").isEmpty()) {
            userRepository.save(User.builder().username("username").build());
        }
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/username"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserByUsername_isNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL)
                        .param("username", "username_is_not_found"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}