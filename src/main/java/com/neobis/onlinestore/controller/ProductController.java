package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.dto.response.ProductResponse;
import com.neobis.onlinestore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@Tag(name = "Product controller", description = "Uses for logic upon products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    @Operation(summary = "Get product", description = "Getting product information by id")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Getting all products information")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update product", description = "Updating product information by id")
    public ResponseEntity<String> updateProductById(@RequestBody ProductRequest request,
                                                    @PathVariable Long id) {
        return productService.updateProductById(request, id);
    }

    @PostMapping
    @Operation(summary = "Save product", description = "Saving new product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request) {
        return productService.saveProduct(request);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete product", description = "Deleting product by id")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}

