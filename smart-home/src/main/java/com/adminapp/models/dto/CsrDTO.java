package com.adminapp.models.dto;

import com.adminapp.models.Csr;
import lombok.Data;

@Data
public class CsrDTO {
    private Long id;
    private String commonName;
    private String organizationName;
    private String organizationUnit;
    private String city;
    private String state;
    private String country;
    private String email;

    public CsrDTO() {}

    public CsrDTO(Csr csr) {
        this.id = csr.getId();
        this.commonName = csr.getCommonName();
        this.organizationName = csr.getOrganizationName();
        this.organizationUnit = csr.getOrganizationUnit();
        this.city = csr.getCity();
        this.state = csr.getState();
        this.country = csr.getCountry();
        this.email = csr.getEmail();
    }

    @Override
    public String toString() {
        return "CN=" + commonName + ",O=" + organizationName + ",OU=" + organizationUnit + ",L=" + city + ",ST=" + state + ",C=" + country + ",EMAIL=" + email;
    }
}
