package com.example.webapp.repository;


import com.example.webapp.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    boolean existsByName(String name);
    Page<Products> findAll(Pageable pageable);
}