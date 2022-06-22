package com.adminapp.dto;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.domain.Certificate;

import java.util.Date;

public class CertificateDTO {

    private String commonName;
    private String organization;
    private String organizationUnit;
    private String locality;
    private String state;
    private String country;
    private String name;
    private Long serialNumber;
    private Date notBefore;
    private Date notAfter;

    public CertificateDTO(){}

    public CertificateDTO(String commonName, String organization, String organizationUnit, String locality, String state, String country, String name, Long serialNumber, Date notBefore, Date notAfter) {
        this.commonName = commonName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.locality = locality;
        this.state = state;
        this.country = country;
        this.name = name;
        this.serialNumber = serialNumber;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
    }

    public CertificateDTO(Certificate certificate){
        this.commonName = certificate.getCommonName();
        this.organization = certificate.getOrganization();
        this.organizationUnit = certificate.getOrganizationUnit();
        this.locality = certificate.getLocality();
        this.state = certificate.getState();
        this.country = certificate.getCountry();
        this.name = certificate.getAlias();
        this.serialNumber = certificate.getSerialNumber();
        this.notBefore = certificate.getDateFrom();
        this.notAfter = certificate.getDateUntil();
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }
}
