package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.ProductRequest;
import com.neobis.onlinestore.dto.response.ProductResponse;
import com.neobis.onlinestore.entity.Product;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.repository.ProductRepository;
import com.neobis.onlinestore.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;

    public ProductResponse getProductByBarcode(Integer barcode) {
        Product product = checkExistAndReturnProductByBarcode(barcode);
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).toList();
    }

    public ResponseEntity<String> saveProduct(ProductRequest request) {
        if (productRepository.findByBarcode(request.getBarcode()).isPresent()) {
            return ResponseEntity.badRequest().body("Product by barcode \"" + request.getBarcode() + "\" is already present!");
        }
        Product product = mapToProduct(request);
        if (request.getProductType() != null) {
            product.setProductType(productTypeRepository.findByName(request.getName().toLowerCase().trim()).orElseThrow(
                    () -> new NotFoundException("Product: product type \n" + request.getProductType() + "\n not exist!")
            ));
        }
        productRepository.save(product);
        return ResponseEntity.ok("Product \"" + request.getName() + "\" has been saved!");
    }

    @Transactional
    public ResponseEntity<String> deleteProductByBarcode(Integer barcode) {
        checkExistAndReturnProductByBarcode(barcode);
        productRepository.deleteByBarcode(barcode);
        return ResponseEntity.ok("Product has been removed!");
    }

    public ResponseEntity<String> updateProductByBarcode(ProductRequest request) {
        Product product = checkExistAndReturnProductByBarcode(request.getBarcode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        if (request.getProductType() != null) {
            product.setProductType(productTypeRepository.findByName(request.getName()).orElseThrow(
                    () -> new NotFoundException("Not found product type name = " + request.getProductType())
            ));
        }
        product.setBarcode(request.getBarcode());
        productRepository.save(product);
        return ResponseEntity.ok().body("Product has been updated!");
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
                .productType(product.getProductType())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
