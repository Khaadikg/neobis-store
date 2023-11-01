package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.dto.response.ProductResponse;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.model.Product;
import com.neobis.onlinestore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse getProductById(Long id) {
        Product product = checkExistAndReturnProduct(id);
        return ProductResponse.builder()
                .name(product.getName())
                .barcode(product.getBarcode())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).toList();
    }

    public ResponseEntity<String> saveProduct(ProductRequest request) {
        if (productRepository.findByBarcode(request.getBarcode()).isPresent()) {
            return ResponseEntity.badRequest().body("Product by barcode \"" + request.getBarcode() + "\" is already present!");
        }
        productRepository.save(mapToProduct(request));
        return ResponseEntity.ok("Product \"" + request.getName() + "\" has been saved!");
    }

    public ResponseEntity<String> deleteProductById(Long id) {
        checkExistAndReturnProduct(id);
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product has been removed!");
    }

    public ResponseEntity<String> updateProductById(ProductRequest request, Long id) {
        Product product = checkExistAndReturnProduct(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBarcode(request.getBarcode());
        productRepository.save(product);
        return ResponseEntity.badRequest().body("Product has been updated!");
    }

    public Product checkExistAndReturnProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No such product found by id = " + id)
        );
    }

    public Product checkExistAndReturnProductByBarcode(Integer barcode) {
        return productRepository.findByBarcode(barcode).orElseThrow(
                () -> new NotFoundException("No such product found by barcode = " + barcode)
        );
    }

    public Product mapToProduct(ProductRequest request) {
        return Product.builder()
                .barcode(request.getBarcode())
                .description(request.getDescription())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .barcode(product.getBarcode())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
