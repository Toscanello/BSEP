package com.adminapp.services;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.dto.RootDTO;

import java.security.PrivateKey;

public interface ICertificateService {

    public void issueCertificate(SubjectData subjectData, IssuerData issuerData);
    public void createRoot(RootDTO rootDTO, PrivateKey privateKey);
}
