package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.dto.response.ProductResponse;
import com.neobis.onlinestore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@Tag(name = "Product controller", description = "Uses for logic upon products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product", description = "Getting product information by id")
    public ProductResponse getProductById(@Positive @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Getting all products information")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping
    @Operation(summary = "Update product", description = "Updating product information by uniq barcode")
    public ResponseEntity<String> updateProductByBarcode(@RequestBody @Valid ProductRequest request) {
        return productService.updateProductByBarcode(request);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save product", description = "Saving new product")
    public ResponseEntity<String> addProduct(@RequestBody @Valid ProductRequest request) {
        return productService.saveProduct(request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Delete product", description = "Deleting product by id")
    public ResponseEntity<String> deleteProductById(@Positive @PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}

