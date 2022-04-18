package com.adminapp.services.impl;

import com.adminapp.crypto.pki.certificates.CertificateGenerator;
import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.crypto.pki.keystores.KeyStoreReader;
import com.adminapp.crypto.pki.keystores.KeyStoreWriter;
import com.adminapp.domain.Certificate;
import com.adminapp.dto.RootDTO;
import com.adminapp.models.Csr;
import com.adminapp.models.dto.IssuerDataDTO;
import com.adminapp.repository.CertificateRepository;
import com.adminapp.services.ICertificateService;
import lombok.AllArgsConstructor;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CertificateService implements ICertificateService {

    private final CSRService csrService;

    private final CertificateRepository certificateRepository;

    @Override
    public X509Certificate issueCertificate(SubjectData subjectData, IssuerData issuerData) {
        X509Certificate certificate = CertificateGenerator.generateCertificate(subjectData, issuerData);
        System.out.println(certificate.toString());
        KeyStoreWriter writer = new KeyStoreWriter();
        writer.loadKeyStore("keystore.jks","password".toCharArray());
        writer.write(IETFUtils.valueToString(subjectData.getX500name().getRDNs(BCStyle.EmailAddress)[0].getFirst().getValue()),
                issuerData.getPrivateKey(),"password".toCharArray(),certificate);
        writer.saveKeyStore("keystore.jks","password".toCharArray());

        return certificate;
    }

    @Override
    public void createRoot(RootDTO rootDTO, PrivateKey privateKey) {

    }

    @Override
    public X509Certificate createCertificate(Long csrId, IssuerDataDTO issuerDataDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        Csr csr = csrService.get(csrId);

        KeyPairGenerator keyPair= KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyPair.initialize(2048, random);

        KeyPair keyPair1 = keyPair.generateKeyPair();
        KeyPair keyPair2 = keyPair.generateKeyPair();

        X500NameBuilder issuerBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        issuerBuilder.addRDN(BCStyle.CN, issuerDataDTO.getCommonName());
        issuerBuilder.addRDN(BCStyle.O, issuerDataDTO.getOrganizationName());
        issuerBuilder.addRDN(BCStyle.OU, issuerDataDTO.getOrganizationUnit());
        issuerBuilder.addRDN(BCStyle.L, issuerDataDTO.getCity());
        issuerBuilder.addRDN(BCStyle.ST, issuerDataDTO.getState());
        issuerBuilder.addRDN(BCStyle.C, issuerDataDTO.getCountry());
        issuerBuilder.addRDN(BCStyle.EmailAddress, issuerDataDTO.getEmail());

        X500NameBuilder subjectBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        subjectBuilder.addRDN(BCStyle.CN, csr.getCommonName());
        subjectBuilder.addRDN(BCStyle.O, csr.getOrganizationName());
        subjectBuilder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        subjectBuilder.addRDN(BCStyle.L, csr.getCity());
        subjectBuilder.addRDN(BCStyle.ST, csr.getState());
        subjectBuilder.addRDN(BCStyle.C, csr.getCountry());
        subjectBuilder.addRDN(BCStyle.EmailAddress, csr.getEmail());

        Certificate certificate = new Certificate();
        certificate.setSerialNumber(Long.parseLong(issuerDataDTO.getSerialNumber()));
        certificate.setAlias(csr.getEmail());
        certificate.setRevoked(false);
        certificate.setCommonName(issuerDataDTO.getCommonName());
        certificate.setDateUntil(issuerDataDTO.getEndDate());
        certificate.setDateFrom(issuerDataDTO.getStartDate());
        certificate.setIsCA(false);
        certificate.setParentCertificate(certificateRepository.findCA());
        certificateRepository.save(certificate);

        return issueCertificate(new SubjectData(keyPair1.getPublic(), subjectBuilder.build(), issuerDataDTO.getSerialNumber(), issuerDataDTO.getStartDate(), issuerDataDTO.getEndDate()),
                new IssuerData(keyPair2.getPrivate(), issuerBuilder.build()));
    }

    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            // kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            // ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if (ks.isKeyEntry(alias)) {
                return (PrivateKey) ks.getKey(alias, pass.toCharArray());
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException |
                CertificateException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean revokeCertificate(Long serialNumber) {
        Certificate certificate = certificateRepository.findBySerialNumber(serialNumber).get();

        if(certificate.getRevoked()) {
            return false;
        }

        certificate.setRevoked(true);
        certificateRepository.save(certificate);

        if(certificate.getIsCA()) {
            revokeCertificatesOfCA(certificate.getSerialNumber());
        }

        return true;
    }

    private void revokeCertificatesOfCA(Long id) {
        List<Certificate> certificatesOfIssuer = certificateRepository.findByIssuer(id);
        certificatesOfIssuer.forEach(e -> revokeCertificate(e.getSerialNumber()));
    }

    public ArrayList<X509Certificate> getAllCertificates(){
         ArrayList<Certificate> certificates = (ArrayList<Certificate>) certificateRepository.findAll();
         ArrayList<X509Certificate> certificatesToReturn = new ArrayList<>();
        KeyStoreReader keyStoreReader = new KeyStoreReader();
         for(Certificate c: certificates){
             certificatesToReturn.add((X509Certificate) keyStoreReader.readCertificate("keystore.jks","password",c.getAlias()));
         }
         return certificatesToReturn;
    }

    @Override
    public boolean validateCert(String alias) {
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        X509Certificate certificate = (X509Certificate) keyStoreReader.readCertificate("keystore.jks","password",alias);
        try {
            certificate.checkValidity();
        } catch (CertificateNotYetValidException | CertificateExpiredException e) {
            return false;
        }
        Certificate certificate1 = certificateRepository.findOneByAlias(alias).orElse(null);

        if(certificate1 != null)
            if(certificate1.getRevoked())
                return false;
        return true;
    }
}
