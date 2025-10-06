package com.polarissoftware.dogs_api.dto;

import com.polarissoftware.dogs_api.entity.CurrentStatus;
import com.polarissoftware.dogs_api.entity.Gender;
import com.polarissoftware.dogs_api.entity.KennellingCharacteristic;
import com.polarissoftware.dogs_api.entity.LeavingReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogDTO {
    private Long id;
    private String name;
    private String breed;
    private Long supplierId;
    private String supplierName;
    private String badgeId;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate dateAcquired;
    private CurrentStatus currentStatus;
    private LocalDate leavingDate;
    private LeavingReason leavingReason;
    private Set<KennellingCharacteristic> kennellingCharacteristics;
}
