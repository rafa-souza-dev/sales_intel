package com.example.salesIntel.repository;

import com.example.salesIntel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
