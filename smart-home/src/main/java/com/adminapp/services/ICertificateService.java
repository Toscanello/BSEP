package com.adminapp.services;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.dto.CertificateDTO;
import com.adminapp.dto.RootDTO;
import com.adminapp.models.dto.IssuerDataDTO;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

public interface ICertificateService {

    CertificateDTO issueCertificate(SubjectData subjectData, IssuerData issuerData);
    void createRoot(RootDTO rootDTO, PrivateKey privateKey);
    CertificateDTO createCertificate(Long csrId, IssuerDataDTO issuerDataDTO) throws NoSuchAlgorithmException, NoSuchProviderException;
    boolean revokeCertificate(Long id);
    List<CertificateDTO> getAllCertificates();
    boolean validateCert(String alias);
}
