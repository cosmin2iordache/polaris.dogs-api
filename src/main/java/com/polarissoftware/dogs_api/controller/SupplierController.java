package com.polarissoftware.dogs_api.controller;

import com.polarissoftware.dogs_api.dto.SupplierDTO;
import com.polarissoftware.dogs_api.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/dogs/suppliers", produces = "application/json")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<SupplierDTO>> list() {
        return ResponseEntity.ok(supplierService.list());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SupplierDTO> create(@RequestBody SupplierDTO dto) {
        SupplierDTO created = supplierService.create(dto);
        return ResponseEntity.created(URI.create("/api/dogs/suppliers/" + created.getId())).body(created);
    }
}
