package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.ProductTypeRequest;
import com.neobis.onlinestore.dto.response.ProductTypeResponse;
import com.neobis.onlinestore.service.ProductTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-type")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "ProductType controller", description = "Uses for logic upon products types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @PostMapping
    @Operation(summary = "Add product type", description = "Adding new product type")
    public String addProductType(@Valid ProductTypeRequest request) {
        return productTypeService.addProductType(request);
    }

    @PutMapping
    @Operation(summary = "Update product type", description = "update product type info")
    public String updateProductType(@NotEmpty @RequestParam String name, @NotEmpty @RequestParam String newName) {
        return productTypeService.updateProductType(name, newName);
    }

    @DeleteMapping
    @Operation(summary = "Delete product type", description = "Deleting product type")
    public String deleteProductType(@Positive @RequestParam Long id) {
        return productTypeService.deleteProductType(id);
    }

    @GetMapping
    @Operation(summary = "Get all product type", description = "Getting all product types")
    public List<ProductTypeResponse> getAllProductTypes() {
        return productTypeService.getAllProductType();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product type", description = "Getting product type by id")
    public ProductTypeResponse getProductTypeById(@Positive @PathVariable("id") Long id) {
        return productTypeService.getProductTypeById(id);
    }

}
