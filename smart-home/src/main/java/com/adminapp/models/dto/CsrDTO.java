package com.adminapp.models.dto;

import com.adminapp.models.Csr;
import lombok.Data;

@Data
public class CsrDTO {
    private Long id;
    private String commonName;
    private String surname;
    private String givenName;
    private String organizationName;
    private String organizationUnit;
    private String country;
    private String email;

    public CsrDTO() {}

    public CsrDTO(Csr csr) {
        this.id = csr.getId();
        this.commonName = csr.getCommonName();
        this.surname = csr.getSurname();
        this.givenName = csr.getGivenName();
        this.organizationName = csr.getOrganizationName();
        this.organizationUnit = csr.getOrganizationUnit();
        this.country = csr.getCountry();
        this.email = csr.getEmail();
    }
}
