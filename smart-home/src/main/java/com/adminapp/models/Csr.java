package com.adminapp.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Csr {
    @Id
    @GeneratedValue
    private Long id;

    private String commonName;
    private String surname;
    private String givenName;
    private String organizationName;
    private String organizationUnit;
    private String country;
    private String email;

    @Column(columnDefinition = "BLOB")
    private byte[] certificate;
}
