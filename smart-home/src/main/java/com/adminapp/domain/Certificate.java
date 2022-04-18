package com.adminapp.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@Entity
public class Certificate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="serial_number", unique=true)
    private Long serialNumber;

    @Column(name="common_name")
    private String commonName;

    @Column(name="alias")
    private String alias;

    @Column(name="revoked")
    private Boolean revoked;

    @Column(name="date_from")
    private Date dateFrom;

    @Column(name="date_until")
    private Date dateUntil;

    @Column(name="is_ca")
    private Boolean isCA;

    @ManyToOne
    @JoinColumn(name="parent_certificate", referencedColumnName = "serial_number")
    private Certificate parentCertificate;
}
