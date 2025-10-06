package com.polarissoftware.dogs_api.repository;

import com.polarissoftware.dogs_api.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByNameIgnoreCase(String name);
}
