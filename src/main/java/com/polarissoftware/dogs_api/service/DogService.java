package com.polarissoftware.dogs_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarissoftware.dogs_api.dto.DogDTO;
import com.polarissoftware.dogs_api.entity.Dog;
import com.polarissoftware.dogs_api.entity.Supplier;
import com.polarissoftware.dogs_api.mapper.DogMapper;
import com.polarissoftware.dogs_api.repository.DogRepository;
import com.polarissoftware.dogs_api.repository.SupplierRepository;
import com.polarissoftware.dogs_api.specification.DogSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final SupplierRepository supplierRepository;
    private final DogMapper dogMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DogDTO create(DogDTO dto) {
        var dog = dogMapper.toEntity(dto);
        dog.setSupplier(resolveSupplier(dto));
        return dogMapper.toDto(dogRepository.save(dog));
    }

    public Page<DogDTO> list(String filterJson, Pageable pageable) {
        Map<String, String> filters = parseFilterJson(filterJson);
        Specification<Dog> spec = Specification
                .where(DogSpecification.notDeleted())
                .and(DogSpecification.fromFilter(filters));

        return dogRepository.findAll(spec, pageable).map(dogMapper::toDto);
    }

    public Optional<DogDTO> get(Long id) {
        return dogRepository.findById(id)
                .filter(dog -> !dog.isDeleted())
                .map(dogMapper::toDto);
    }

    public Optional<DogDTO> update(Long id, DogDTO dto) {
        return dogRepository.findById(id).map(existing -> {
            updateFields(existing, dto);
            existing.setSupplier(resolveSupplier(dto));
            return dogMapper.toDto(dogRepository.save(existing));
        });
    }

    public boolean softDelete(Long id) {
        return dogRepository.findById(id).map(dog -> {
            dog.setDeleted(true);
            dogRepository.save(dog);
            return true;
        }).orElse(false);
    }

    public List<DogDTO> getAllDogs() {
        return dogRepository.findAll().stream()
                .filter(dog -> !dog.isDeleted())
                .map(dogMapper::toDto)
                .collect(Collectors.toList());
    }

    // ---------- Private helpers ----------

    private Supplier resolveSupplier(DogDTO dto) {
        if (dto.getSupplierId() != null) {
            return supplierRepository.findById(dto.getSupplierId()).orElse(null);
        }
        if (dto.getSupplierName() != null) {
            return supplierRepository.findByNameIgnoreCase(dto.getSupplierName())
                    .orElseGet(() -> supplierRepository.save(
                            Supplier.builder().name(dto.getSupplierName()).build()));
        }
        return null;
    }

    private Map<String, String> parseFilterJson(String filterJson) {
        if (filterJson == null || filterJson.isBlank()) return Collections.emptyMap();
        try {
            return objectMapper.readValue(filterJson, new TypeReference<>() {});
        } catch (Exception ignored) {
            return Collections.emptyMap();
        }
    }

    private void updateFields(Dog existing, DogDTO dto) {
        Optional.ofNullable(dto.getName()).ifPresent(existing::setName);
        Optional.ofNullable(dto.getBreed()).ifPresent(existing::setBreed);
        Optional.ofNullable(dto.getBadgeId()).ifPresent(existing::setBadgeId);
        Optional.ofNullable(dto.getGender()).ifPresent(existing::setGender);
        Optional.ofNullable(dto.getBirthDate()).ifPresent(existing::setBirthDate);
        Optional.ofNullable(dto.getDateAcquired()).ifPresent(existing::setDateAcquired);
        Optional.ofNullable(dto.getCurrentStatus()).ifPresent(existing::setCurrentStatus);
        Optional.ofNullable(dto.getLeavingDate()).ifPresent(existing::setLeavingDate);
        Optional.ofNullable(dto.getLeavingReason()).ifPresent(existing::setLeavingReason);
        Optional.ofNullable(dto.getKennellingCharacteristics()).ifPresent(existing::setKennellingCharacteristics);
    }
}
