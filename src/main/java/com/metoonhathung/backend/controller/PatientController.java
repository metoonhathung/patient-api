package com.metoonhathung.backend.controller;

import com.metoonhathung.backend.model.Patient;
import com.metoonhathung.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/get")
    public ResponseEntity<List<Patient>> get(@RequestParam String condition, @RequestParam String range) {
        return patientService.get(condition, range);
    }

    @GetMapping("/count")
    public ResponseEntity<BigInteger> count(@RequestParam String condition) {
        return patientService.count(condition);
    }

    @GetMapping("/read")
    public ResponseEntity<Patient> read(@RequestParam Long id) {
        return patientService.read(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @PutMapping("/update")
    public ResponseEntity<Patient> update(@RequestParam Long id, @RequestBody Patient updatedPatient) {
        return patientService.update(id, updatedPatient);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Boolean>> delete(@RequestParam Long id) {
        return patientService.delete(id);
    }
}
