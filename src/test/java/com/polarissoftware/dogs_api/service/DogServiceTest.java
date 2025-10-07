package com.polarissoftware.dogs_api.service;

import com.polarissoftware.dogs_api.dto.DogDTO;
import com.polarissoftware.dogs_api.entity.Dog;
import com.polarissoftware.dogs_api.entity.Supplier;
import com.polarissoftware.dogs_api.enums.Gender;
import com.polarissoftware.dogs_api.mapper.DogMapper;
import com.polarissoftware.dogs_api.repository.DogRepository;
import com.polarissoftware.dogs_api.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogServiceTest {

    @Mock
    private DogRepository dogRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private DogMapper dogMapper;

    @InjectMocks
    private DogService dogService;
    private Pageable pageable;

    private Dog dog;
    private DogDTO dogDTO;

    @BeforeEach
    void setUp() {

        Supplier supplier = Supplier.builder().id(1L).name("Paws Training Co.").build();

        dog = Dog.builder()
                .id(1L)
                .name("Max")
                .breed("Labrador")
                .gender(Gender.MALE)
                .supplier(supplier)
                .deleted(false)
                .build();

        dogDTO = DogDTO.builder()
                .id(1L)
                .name("Max")
                .breed("Labrador")
                .gender(Gender.MALE)
                .supplierId(1L)
                .build();
    }

    @Test
    void testCreateDog_withExistingSupplier() {
        when(dogMapper.toEntity(dogDTO)).thenReturn(dog);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(dog.getSupplier()));
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);
        when(dogMapper.toDto(any(Dog.class))).thenReturn(dogDTO);

        DogDTO result = dogService.create(dogDTO);

        assertThat(result.getName()).isEqualTo("Max");
        verify(dogRepository, times(1)).save(any(Dog.class));
    }

    @Test
    void testGetDogById_found() {
        when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));
        when(dogMapper.toDto(dog)).thenReturn(dogDTO);

        Optional<DogDTO> result = dogService.get(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Max");
    }

    @Test
    void testGetDogById_notFound() {
        when(dogRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<DogDTO> result = dogService.get(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void testSoftDelete_success() {
        when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));

        boolean result = dogService.softDelete(1L);

        assertThat(result).isTrue();
        assertThat(dog.isDeleted()).isTrue();
        verify(dogRepository, times(1)).save(dog);
    }

    @Test
    void testSoftDelete_notFound() {
        when(dogRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = dogService.softDelete(99L);

        assertThat(result).isFalse();
        verify(dogRepository, never()).save(any());
    }

    @Test
    void testGetAllDogs_returnsNonDeleted() {
        Dog deletedDog = Dog.builder().id(2L).name("Bella").deleted(true).build();
        when(dogRepository.findAll()).thenReturn(List.of(dog, deletedDog));
        when(dogMapper.toDto(dog)).thenReturn(dogDTO);

        List<DogDTO> result = dogService.getAllDogs();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Max");
    }

    @Test
    void testList_WithEmptyFilter_ReturnsAllDogs() {
        // given
        String filterJson = null;

        Dog dog = new Dog();
        dog.setName("Bella");

        DogDTO dto = new DogDTO();
        dto.setName("Bella");

        Page<Dog> dogPage = new PageImpl<>(List.of(dog));
        when(dogRepository.findAll((Specification<Dog>) any(), eq(pageable))).thenReturn(dogPage);
        when(dogMapper.toDto(any(Dog.class))).thenReturn(dto);

        // when
        Page<DogDTO> result = dogService.list(filterJson, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Bella");
    }

    @Test
    void testUpdateDog_success() {
        // given
        DogDTO updateDto = DogDTO.builder()
                .name("Buddy")
                .breed("German Shepherd")
                .supplierId(1L)
                .build();

        Supplier supplier = Supplier.builder()
                .id(1L)
                .name("Paws Training Co.")
                .build();

        Dog updatedDog = Dog.builder()
                .id(1L)
                .name("Buddy")
                .breed("German Shepherd")
                .supplier(supplier)
                .deleted(false)
                .build();

        DogDTO expectedDto = DogDTO.builder()
                .id(1L)
                .name("Buddy")
                .breed("German Shepherd")
                .supplierId(1L)
                .build();

        when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(dogRepository.save(any(Dog.class))).thenReturn(updatedDog);
        when(dogMapper.toDto(any(Dog.class))).thenReturn(expectedDto);

        // when
        Optional<DogDTO> result = dogService.update(1L, updateDto);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Buddy");
        assertThat(result.get().getBreed()).isEqualTo("German Shepherd");
        assertThat(result.get().getSupplierId()).isEqualTo(1L);

        verify(dogRepository).findById(1L);
        verify(dogRepository).save(any(Dog.class));
        verify(dogMapper).toDto(any(Dog.class));
    }

}
