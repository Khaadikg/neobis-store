package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.dto.response.ProductResponse;
import com.neobis.onlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateProductById(@RequestBody ProductRequest request,
                                                   @PathVariable Long id) {
        return productService.updateProductById(request, id);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request) {
        return productService.saveProduct(request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}
