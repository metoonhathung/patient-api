package com.metoonhathung.patient.controller;

import com.metoonhathung.patient.api.PatientsApi;
import com.metoonhathung.patient.model.Pagination;
import com.metoonhathung.patient.model.PatientGetResponse;
import com.metoonhathung.patient.model.PatientPostRequest;
import com.metoonhathung.patient.model.PatientPutRequest;
import com.metoonhathung.patient.model.PatientsGetRequest;
import com.metoonhathung.patient.model.PatientsGetResponse;
import com.metoonhathung.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

@CrossOrigin
@Controller
@Primary
public class PatientController implements PatientsApi {

    @Autowired
    private PatientService patientService;

    @Override
    public ResponseEntity<PatientsGetResponse> getPatients(@Valid PatientsGetRequest patientsGetRequest, @Valid Pagination pagination) {
        PatientsGetResponse response = patientService.getPatients(patientsGetRequest, pagination);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<PatientGetResponse> getPatient(Long id) {
        PatientGetResponse response = patientService.getPatient(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> postPatient(@Valid PatientPostRequest patientPostRequest) {
        patientService.postPatient(patientPostRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> putPatient(Long id, @Valid PatientPutRequest patientPutRequest) {
        patientService.putPatient(id, patientPutRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deletePatient(Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}
