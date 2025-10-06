package com.polarissoftware.dogs_api.service;

import com.polarissoftware.dogs_api.dto.SupplierDTO;
import com.polarissoftware.dogs_api.entity.Supplier;
import com.polarissoftware.dogs_api.mapper.SupplierMapper;
import com.polarissoftware.dogs_api.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;


    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }


    public SupplierDTO create(SupplierDTO dto) {
        Supplier s = supplierMapper.toEntity(dto);
        return supplierMapper.toDto(supplierRepository.save(s));
    }


    public List<SupplierDTO> list() {
        return supplierRepository.findAll().stream().map(supplierMapper::toDto).collect(Collectors.toList());
    }
}
