package com.metoonhathung.patient.service;

import com.metoonhathung.patient.model.Pagination;
import com.metoonhathung.patient.model.PatientGetResponse;
import com.metoonhathung.patient.model.PatientPostRequest;
import com.metoonhathung.patient.model.PatientPutRequest;
import com.metoonhathung.patient.model.PatientsGetRequest;
import com.metoonhathung.patient.model.PatientsGetResponse;

public interface IPatientService {
    PatientsGetResponse getPatients(PatientsGetRequest patientsGetRequest, Pagination pagination);

    PatientGetResponse getPatient(Long id);

    void postPatient(PatientPostRequest patientPostRequest);

    void putPatient(Long id, PatientPutRequest patientPutRequest);

    void deletePatient(Long id);
}
