package com.example.library.management.repositories;

import com.example.library.management.entities.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowEntity, Long> {

    List<BorrowEntity> findByUserId(Long userId);
}