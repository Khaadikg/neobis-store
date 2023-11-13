package com.neobis.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.entity.Product;
import com.neobis.onlinestore.repository.ProductRepository;
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
class ProductControllerTest {

    private final String URL = "/api/products";
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private WebApplicationContext webApplicationContext;
    private final ProductRepository productRepository;

    @Autowired
    public ProductControllerTest(ObjectMapper mapper, WebApplicationContext webApplicationContext, ProductRepository productRepository) {
        this.mapper = mapper;
        this.webApplicationContext = webApplicationContext;
        this.productRepository = productRepository;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getProductById() throws Exception {
        productRepository.save(new Product());
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAllProducts() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void updateProductByBarcode() throws Exception {
        if (productRepository.findByBarcode(123).isEmpty()) {
            productRepository.save(Product.builder().barcode(123).price(12.0).build());
        }
        this.mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ProductRequest.builder().barcode(123).price(13.0).build())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void addProduct() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ProductRequest.builder().barcode(124).price(13.0).build())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void deleteProductById() throws Exception {
        productRepository.save(Product.builder().barcode(123).price(12.0).build());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}