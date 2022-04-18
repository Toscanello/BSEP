package com.adminapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Certificate {
    @Id
    @GeneratedValue
    private Long id;

    private Long serialNumber;
    private String commonName;
    private String alias;
    private Boolean revoked;
    private Date dateFrom;
    private Date dateUntil;
}
