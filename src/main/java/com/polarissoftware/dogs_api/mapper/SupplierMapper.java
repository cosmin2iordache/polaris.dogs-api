package com.polarissoftware.dogs_api.mapper;

import com.polarissoftware.dogs_api.dto.SupplierDTO;
import com.polarissoftware.dogs_api.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDTO toDto(Supplier s);
    Supplier toEntity(SupplierDTO dto);
}

