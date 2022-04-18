package com.adminapp.services.impl;

import com.adminapp.crypto.pki.certificates.CertificateGenerator;
import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.crypto.pki.keystores.KeyStoreWriter;
import com.adminapp.dto.RootDTO;
import com.adminapp.services.ICertificateService;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Service
public class CertificateService implements ICertificateService {

    @Override
    public void issueCertificate(SubjectData subjectData, IssuerData issuerData) {
        X509Certificate certificate = CertificateGenerator.generateCertificate(subjectData,issuerData);
        System.out.println(certificate.toString());
        KeyStoreWriter writer = new KeyStoreWriter();
        writer.loadKeyStore(null,"password".toCharArray());
        writer.write("neki email",issuerData.getPrivateKey(),"password".toCharArray(),certificate);
        writer.saveKeyStore("keystore","password".toCharArray());
    }

    @Override
    public void createRoot(RootDTO rootDTO, PrivateKey privateKey) {

    }


}
