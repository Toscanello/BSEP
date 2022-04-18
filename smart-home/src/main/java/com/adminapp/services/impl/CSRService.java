package com.adminapp.services.impl;

import com.adminapp.models.Csr;
import com.adminapp.models.dto.CsrDTO;
import com.adminapp.repository.CSRRepository;
import com.adminapp.services.ICSRService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.security.*;
import java.util.List;

public class CSRService implements ICSRService {

    @Autowired
    private CSRRepository csrRepository;

    public List<Csr> getAll() {
        return csrRepository.findAll();
    }

    public Csr get(Long id) {
        return csrRepository.findById(id).orElseGet(null);
    }

    @Override
    public void createCSR(CsrDTO csrDTO) {
        KeyPair pair = generateKeyPair();

        Csr csrDb = new Csr();
        csrDb.setCommonName(csrDTO.getCommonName());
        csrDb.setSurname(csrDTO.getSurname());
        csrDb.setGivenName(csrDTO.getGivenName());
        csrDb.setOrganizationName(csrDTO.getOrganizationName());
        csrDb.setOrganizationUnit(csrDTO.getOrganizationUnit());
        csrDb.setCountry(csrDTO.getCountry());

    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
