package com.polarissoftware.dogs_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String badgeId;

    @ElementCollection(targetClass = KennellingCharacteristic.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dog_kennelling_characteristics", joinColumns = @JoinColumn(name = "dog_id"))
    @Column(name = "characteristic")
    @Builder.Default
    private Set<KennellingCharacteristic> kennellingCharacteristics = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;
    private LocalDate dateAcquired;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    private LocalDate leavingDate;

    @Enumerated(EnumType.STRING)
    private LeavingReason leavingReason;

    private boolean deleted = false;
}
