package com.neobis.onlinestore.repository;

import com.neobis.onlinestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarcode(Integer barcode);
}
