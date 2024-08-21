package com.example.webapp.repository;

import com.example.webapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    List<OrderDetails> findByOrderId (Long orderId);
}
