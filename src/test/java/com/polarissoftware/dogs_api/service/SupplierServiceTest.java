package com.polarissoftware.dogs_api.service;

import com.polarissoftware.dogs_api.dto.SupplierDTO;
import com.polarissoftware.dogs_api.entity.Supplier;
import com.polarissoftware.dogs_api.mapper.SupplierMapper;
import com.polarissoftware.dogs_api.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    void testCreate_ShouldSaveAndReturnMappedDto() {
        // given
        SupplierDTO inputDto = new SupplierDTO();
        inputDto.setName("John Supplies");

        Supplier entity = new Supplier();
        entity.setName("John Supplies");

        Supplier savedEntity = new Supplier();
        savedEntity.setId(1L);
        savedEntity.setName("John Supplies");

        SupplierDTO outputDto = new SupplierDTO();
        outputDto.setId(1L);
        outputDto.setName("John Supplies");

        when(supplierMapper.toEntity(inputDto)).thenReturn(entity);
        when(supplierRepository.save(entity)).thenReturn(savedEntity);
        when(supplierMapper.toDto(savedEntity)).thenReturn(outputDto);

        // when
        SupplierDTO result = supplierService.create(inputDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Supplies");

        verify(supplierMapper).toEntity(inputDto);
        verify(supplierRepository).save(entity);
        verify(supplierMapper).toDto(savedEntity);
    }

    @Test
    void testList_ShouldReturnAllMappedDtos() {
        // given
        Supplier s1 = new Supplier(1L, "Supplier A");
        Supplier s2 = new Supplier(2L, "Supplier B");
        List<Supplier> entities = List.of(s1, s2);

        SupplierDTO dto1 = new SupplierDTO(1L, "Supplier A");
        SupplierDTO dto2 = new SupplierDTO(2L, "Supplier B");

        when(supplierRepository.findAll()).thenReturn(entities);
        when(supplierMapper.toDto(s1)).thenReturn(dto1);
        when(supplierMapper.toDto(s2)).thenReturn(dto2);

        // when
        List<SupplierDTO> result = supplierService.list();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.stream().map(SupplierDTO::getName).collect(Collectors.toList()))
                .containsExactly("Supplier A", "Supplier B");

        verify(supplierRepository).findAll();
        verify(supplierMapper, times(2)).toDto(any(Supplier.class));
    }
}
