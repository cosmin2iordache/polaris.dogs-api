package com.polarissoftware.dogs_api.repository;

import com.polarissoftware.dogs_api.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DogRepository extends JpaRepository<Dog, Long>, JpaSpecificationExecutor<Dog> {
}
