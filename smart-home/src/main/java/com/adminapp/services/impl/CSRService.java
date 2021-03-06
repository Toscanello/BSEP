package com.adminapp.services.impl;

import com.adminapp.models.Csr;
import com.adminapp.models.dto.CsrDTO;
import com.adminapp.repository.CSRRepository;
import com.adminapp.services.ICSRService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.List;

@Service
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
    public String createCSR(CsrDTO csrDTO) throws OperatorCreationException, IOException {
        KeyPair pair = generateKeyPair();
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(new X500Principal(csrDTO.toString()), pair.getPublic());
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(pair.getPrivate());
        PKCS10CertificationRequest csr = p10Builder.build(signer);
        System.out.println(csr.getSubject());

        PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csr.getEncoded());
        StringWriter str = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(str);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        str.close();

        System.out.println("Private key: " + Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));

        Csr csrDb = new Csr();
        csrDb.setCommonName(csrDTO.getCommonName());
        csrDb.setOrganizationName(csrDTO.getOrganizationName());
        csrDb.setOrganizationUnit(csrDTO.getOrganizationUnit());
        csrDb.setCity(csrDTO.getCity());
        csrDb.setState(csrDTO.getState());
        csrDb.setCountry(csrDTO.getCountry());
        csrDb.setEmail(csrDTO.getEmail());
        csrDb.setCertificate(csr.getEncoded());

        csrRepository.save(csrDb);

        return str.toString();

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
