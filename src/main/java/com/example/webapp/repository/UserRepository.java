package com.example.webapp.repository;

import com.example.webapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   boolean existsByPhoneNumber(String phoneNumber);
   Optional<User> findByPhoneNumber(String phoneNumber);
//   boolean existsByName(String name);
}
