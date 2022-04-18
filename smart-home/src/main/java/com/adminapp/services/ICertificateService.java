package com.adminapp.services;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.dto.RootDTO;
import com.adminapp.models.dto.IssuerDataDTO;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

public interface ICertificateService {

    X509Certificate issueCertificate(SubjectData subjectData, IssuerData issuerData);
    void createRoot(RootDTO rootDTO, PrivateKey privateKey);
    X509Certificate createCertificate(Long csrId, IssuerDataDTO issuerDataDTO) throws NoSuchAlgorithmException, NoSuchProviderException;
    boolean revokeCertificate(Long id);
    List<X509Certificate> getAllCertificates();
    boolean validateCert(String alias);
}
