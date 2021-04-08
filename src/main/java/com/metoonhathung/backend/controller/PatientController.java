package com.metoonhathung.backend.controller;

import com.metoonhathung.backend.exception.ResourceNotFoundException;
import com.metoonhathung.backend.model.Patient;
import com.metoonhathung.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    //get all patients
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    //get patient by id
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long patientId)
            throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id :: " + patientId));
        return ResponseEntity.ok().body(patient);
    }

    //create patient
    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        patient.setCreated(timestamp);
        patient.setUpdated(timestamp);
        return patientRepository.save(patient);
    }

    //update patient
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable(value = "id") Long patientId, @RequestBody Patient patientDetails) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id :: " + patientId));
        patient.setName(patientDetails.getName());
        patient.setGender(patientDetails.getGender());
        patient.setAge(patientDetails.getAge());
        patient.setEmail(patientDetails.getEmail());
        patient.setPhone(patientDetails.getPhone());
        patient.setUpdated(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(patientRepository.save(patient));
    }

    //delete patient
    @DeleteMapping("/patients/{id}")
    public Map<String, Boolean> deletePatient(@PathVariable(value = "id") Long patientId)
            throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id :: " + patientId));
        patientRepository.delete(patient);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
