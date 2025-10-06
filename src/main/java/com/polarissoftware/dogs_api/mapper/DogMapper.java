package com.polarissoftware.dogs_api.mapper;

import com.polarissoftware.dogs_api.dto.DogDTO;
import com.polarissoftware.dogs_api.entity.Dog;
import com.polarissoftware.dogs_api.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogMapper {

    @Mapping(source = "supplier.name", target = "supplierName")
    DogDTO toDto(Dog dog);

    @Mapping(target = "supplier", ignore = true)
    Dog toEntity(DogDTO dto);

}
