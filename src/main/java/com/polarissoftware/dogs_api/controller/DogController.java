package com.polarissoftware.dogs_api.controller;

import com.polarissoftware.dogs_api.dto.DogDTO;
import com.polarissoftware.dogs_api.service.DogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/dogs", produces = "application/json")
@Validated
public class DogController {
    private final DogService dogService;


    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<Page<DogDTO>> list(@RequestParam(value = "filter", required = false) String filter,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DogDTO> result = dogService.list(filter, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DogDTO> create(@RequestBody DogDTO dto) {
        DogDTO created = dogService.create(dto);
        return ResponseEntity.created(URI.create("/api/dogs/dogs/" + created.getId())).body(created);
    }

    @GetMapping(path = "/dog/{id}", produces = "application/json")
    public ResponseEntity<DogDTO> get(@PathVariable Long id) {
        Optional<DogDTO> dto = dogService.get(id);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DogDTO> update(@PathVariable Long id, @RequestBody DogDTO dto) {
        Optional<DogDTO> updated = dogService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean ok = dogService.softDelete(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
