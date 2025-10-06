package com.polarissoftware.dogs_api.mapper;

import com.polarissoftware.dogs_api.dto.DogDTO;
import com.polarissoftware.dogs_api.entity.Dog;
import com.polarissoftware.dogs_api.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogMapper {

    // ✅ Entity → DTO (for GET)
    @Mapping(source = "supplier.name", target = "supplierName")
    DogDTO toDto(Dog dog);

    // ✅ DTO → Entity (for POST/PUT)
    // We ignore supplier to avoid "Unknown property" error
    @Mapping(target = "supplier", ignore = true)
    Dog toEntity(DogDTO dto);

//    // ✅ Optional helper method (not used directly by MapStruct here)
//    default Supplier toSupplier(String name) {
//        if (name == null || name.isEmpty()) return null;
//        return Supplier.builder().name(name).build();
//    }
}
