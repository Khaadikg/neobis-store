package com.neobis.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.onlinestore.dto.request.ProductTypeRequest;
import com.neobis.onlinestore.entity.ProductType;
import com.neobis.onlinestore.repository.ProductTypeRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductTypeControllerTest {

    private final String URL = "/api/product-type";
    private final ProductTypeRequest productTypeRequest
            = ProductTypeRequest.builder()
            .name("onion")
            .build();
    private final ProductType productType = ProductType.builder().name("onion").build();
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private WebApplicationContext webApplicationContext;

    private ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeControllerTest(ObjectMapper mapper, WebApplicationContext webApplicationContext, ProductTypeRepository productTypeRepository) {
        this.mapper = mapper;
        this.webApplicationContext = webApplicationContext;
        this.productTypeRepository = productTypeRepository;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void addProductType() throws Exception {
        ProductTypeRequest request
                = ProductTypeRequest.builder()
                .name("onion")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void updateProductType() throws Exception {
        productTypeRepository.save(productType);
        this.mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .param("name", "onion")
                        .param("newName", "apple"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAllProductTypes() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getProductTypeById() throws Exception {
        addProductType();
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void deleteProductType() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL).param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}