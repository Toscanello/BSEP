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
    private String organizationName;
    private String organizationUnit;
    private String city;
    private String state;
    private String country;
    private String email;

    @Column(columnDefinition = "bytea")
    private byte[] certificate;

    @Override
    public String toString() {
        return "CN=" + commonName + ",O=" + organizationName + ",OU=" + organizationUnit + ",L=" + city + ",ST=" + state + ",C=" + country + ",EMAIL=" + email;
    }
}
