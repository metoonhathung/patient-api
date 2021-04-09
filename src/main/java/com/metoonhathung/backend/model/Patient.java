package com.metoonhathung.backend.model;

import javax.persistence.*;
import java.util.*;
import lombok.Data;


@Table(name = "patient")
@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private Date created;

    @Column
    private Date updated;
}
