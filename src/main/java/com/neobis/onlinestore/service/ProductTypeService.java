package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.ProductTypeRequest;
import com.neobis.onlinestore.dto.response.ProductTypeResponse;
import com.neobis.onlinestore.entity.ProductType;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public String addProductType(ProductTypeRequest request) {
        if (productTypeRepository.findByName(request.getName()).isPresent()) {
            return request.getName() + " is already exist";
        }
        productTypeRepository.save(mapProductTypeRequestToProductType(request));
        return request.getName() + " successfully saved!";
    }

    public String deleteProductType(Long id) {
        productTypeRepository.deleteById(id);
        return "Product type successfully deleted!";
    }

    public String updateProductType(String name, String newName) {
        ProductType productType = productTypeRepository.findByName(name.toLowerCase().trim()).orElseThrow(
                () -> new NotFoundException("ProductType didnt found by name  = " + name)
        );
        productType.setName(newName);
        productTypeRepository.save(productType);
        return "Product type successfully updated!";
    }

    public List<ProductTypeResponse> getAllProductType() {
        return productTypeRepository.findAll().stream().map(this::mapProductTypeToProductTypeResponse).toList();
    }

    public ProductTypeResponse getProductTypeById(Long id) {
        return mapProductTypeToProductTypeResponse(productTypeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ProductType didnt found by id = " + id)
        ));
    }

    public ProductType mapProductTypeRequestToProductType(ProductTypeRequest request) {
        return ProductType.builder().name(request.getName()).build();
    }

    public ProductTypeResponse mapProductTypeToProductTypeResponse(ProductType productType) {
        return ProductTypeResponse.builder().name(productType.getName()).build();
    }

}
