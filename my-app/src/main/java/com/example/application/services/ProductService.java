package com.example.application.services;

import com.example.application.data.Product;
import com.example.application.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(boolean isAdmin) {
        if (isAdmin) {
            return productRepo.findAll();
        }
        return productRepo.findByIsActive(1);

    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public void delete(Product product) {
        productRepo.delete(product);
    }
}
