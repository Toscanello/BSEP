package com.adminapp.controllers;

import com.adminapp.models.Csr;
import com.adminapp.models.dto.CsrDTO;
import com.adminapp.services.ICSRService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "csr")
public class CSRController {

    @Autowired
    public ICSRService service;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> createCSR(@RequestBody CsrDTO csrDTO) throws IOException, OperatorCreationException {
        String res = service.createCSR(csrDTO);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getAllCsrs() {
        return new ResponseEntity<>(service.getAll().stream().map(csr -> new CsrDTO(csr)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCsr(@PathVariable Long id) {
        Csr csr = service.get(id);
        if (csr == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new CsrDTO(csr), HttpStatus.OK);
        }
    }

}
