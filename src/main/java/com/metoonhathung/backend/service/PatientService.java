package com.metoonhathung.backend.service;

import com.metoonhathung.backend.model.Patient;
import com.metoonhathung.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.*;

@Service
public class PatientService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    public ResponseEntity<List<Patient>> get(String condition, String range) {
        String query = "SELECT * FROM patient " + condition + " ORDER BY id " + range;
        List<Patient> patients = entityManager.createNativeQuery(query, Patient.class).getResultList();
        return ResponseEntity.ok(patients);
    }

    public ResponseEntity<BigInteger> count(String condition) {
        String query = "SELECT COUNT(*) FROM patient " + condition;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(query).getSingleResult();
        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Patient> read(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        return ResponseEntity.ok().body(patient);
    }

    public ResponseEntity<Patient> create(Patient patient) {
        Date timestamp = new Date();
        patient.setCreated(timestamp);
        patient.setUpdated(timestamp);
        return ResponseEntity.ok(patientRepository.save(patient));
    }

    public ResponseEntity<Patient> update(Long id, Patient updatedPatient) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        updatedPatient.setId(id);
        updatedPatient.setUpdated(new Date());
        updatedPatient.setCreated(patient.getCreated());
        return ResponseEntity.ok(patientRepository.save(updatedPatient));
    }

    public ResponseEntity<Map<String, Boolean>> delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        patientRepository.delete(patient);
        Map<String, Boolean> map = new HashMap<>();
        map.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(map);
    }
}
