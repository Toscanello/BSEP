package com.adminapp.services;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.dto.RootDTO;
import com.adminapp.models.dto.IssuerDataDTO;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface ICertificateService {

    public X509Certificate issueCertificate(SubjectData subjectData, IssuerData issuerData);
    public void createRoot(RootDTO rootDTO, PrivateKey privateKey);
    public X509Certificate createCertificate(Long csrId, IssuerDataDTO issuerDataDTO) throws NoSuchAlgorithmException, NoSuchProviderException;
    boolean revokeCertificate(Long id);
}
