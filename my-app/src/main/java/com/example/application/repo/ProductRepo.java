package com.example.application.repo;

import com.example.application.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByIsActive(int isActive);
}
