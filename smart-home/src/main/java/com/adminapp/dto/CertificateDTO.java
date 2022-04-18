package com.adminapp.dto;

import com.adminapp.domain.Certificate;

public class CertificateDTO {
    private String proba;
    public CertificateDTO(){}
    public CertificateDTO(String proba){this.proba = proba;}

    public String getProba() {
        return proba;
    }

    public void setProba(String proba) {
        this.proba = proba;
    }
}
