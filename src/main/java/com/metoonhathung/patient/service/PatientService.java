package com.metoonhathung.patient.service;

import com.metoonhathung.patient.exception.NotFoundException;
import com.metoonhathung.patient.model.MetaResponse;
import com.metoonhathung.patient.model.Pagination;
import com.metoonhathung.patient.model.Patient;
import com.metoonhathung.patient.model.PatientGetResponse;
import com.metoonhathung.patient.model.PatientPostRequest;
import com.metoonhathung.patient.model.PatientPutRequest;
import com.metoonhathung.patient.model.PatientsGetRequest;
import com.metoonhathung.patient.model.PatientsGetResponse;
import com.metoonhathung.patient.model.QPatient;
import com.metoonhathung.patient.repository.PatientRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService implements IPatientService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final QPatient qPatient = QPatient.patient;

    @Override
    public PatientsGetResponse getPatients(PatientsGetRequest patientsGetRequest, Pagination pagination) {
        JPAQuery<Patient> query = new JPAQuery<>(entityManager);
        PathBuilder<Patient> pathBuilder = new PathBuilder<>(Patient.class, "patient");
        List<Patient> patients = query
                .from(qPatient)
                .where(buildPredicate(patientsGetRequest))
                .limit(pagination.getSize())
                .offset((long) (pagination.getPage() - 1) * pagination.getSize())
                .orderBy(pagination.getDirection().equals("DESC")
                        ? pathBuilder.getString(pagination.getOrderBy()).desc()
                        : pathBuilder.getString(pagination.getOrderBy()).asc()
                )
                .fetch();
        List<PatientGetResponse> data = patients
                .stream()
                .map(patient -> modelMapper.map(patient, PatientGetResponse.class))
                .collect(Collectors.toList());
        MetaResponse meta = new MetaResponse();
        meta.setTotal(query.fetchCount());
        PatientsGetResponse response = new PatientsGetResponse();
        response.setMeta(meta);
        response.setData(data);
        return response;
    }

    @Override
    public PatientGetResponse getPatient(Long id) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        return modelMapper.map(patient, PatientGetResponse.class);
    }

    @Override
    public void postPatient(PatientPostRequest patientPostRequest) {
        Patient patient = modelMapper.map(patientPostRequest, Patient.class);
        Date timestamp = new Date();
        patient.setCreatedAt(timestamp);
        patient.setUpdatedAt(timestamp);
        patientRepository.save(patient);
    }

    @Override
    public void putPatient(Long id, PatientPutRequest patientPutRequest) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        modelMapper.map(patientPutRequest, patient);
        Date timestamp = new Date();
        patient.setUpdatedAt(timestamp);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        patientRepository.delete(patient);
    }

    private BooleanBuilder buildPredicate(PatientsGetRequest patientsGetRequest) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(patientsGetRequest.getId())) {
            booleanBuilder.and(qPatient.id.eq(patientsGetRequest.getId()));
        }
        if (StringUtils.isNotBlank(patientsGetRequest.getName())) {
            booleanBuilder.and(qPatient.name.containsIgnoreCase(patientsGetRequest.getName()));
        }
        if (StringUtils.isNotBlank(patientsGetRequest.getGender())) {
            booleanBuilder.and(qPatient.gender.eq(patientsGetRequest.getGender()));
        }
        if (Objects.nonNull(patientsGetRequest.getAge())) {
            booleanBuilder.and(qPatient.age.eq(patientsGetRequest.getAge()));
        }
        if (StringUtils.isNotBlank(patientsGetRequest.getEmail())) {
            booleanBuilder.and(qPatient.email.containsIgnoreCase(patientsGetRequest.getEmail()));
        }
        if (StringUtils.isNotBlank(patientsGetRequest.getPhoneNumber())) {
            booleanBuilder.and(qPatient.phoneNumber.containsIgnoreCase(patientsGetRequest.getPhoneNumber()));
        }
        return booleanBuilder;
    }
}
