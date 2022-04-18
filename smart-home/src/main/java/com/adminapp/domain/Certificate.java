package com.adminapp.domain;

import java.util.Date;

public class Certificate {
    private Long serialNumber;
    private String commonName;
    private String alias;
    private Boolean revoked;
    private Date dateFrom;
    private Date dateUntil;
}
