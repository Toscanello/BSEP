package com.adminapp.controllers;

import com.adminapp.crypto.pki.data.IssuerData;
import com.adminapp.crypto.pki.data.SubjectData;
import com.adminapp.dto.CertificateDTO;
import com.adminapp.models.dto.IssuerDataDTO;
import com.adminapp.services.ICertificateService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@RestController
    @RequestMapping(value = "certificates")
public class CertificateController {
    @Autowired
    public ICertificateService certificateService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> issueCertificate(@RequestBody CertificateDTO certificateDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPair= KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyPair.initialize(2048, random);
        KeyPair keyPair1 = keyPair.generateKeyPair();
        KeyPair keyPair2 = keyPair.generateKeyPair();
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Subject");
        builder.addRDN(BCStyle.O, "House");
        builder.addRDN(BCStyle.OU, "Admin");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        X500NameBuilder builder2 = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Root");
        builder.addRDN(BCStyle.O, "House");
        builder.addRDN(BCStyle.OU, "Admin");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        certificateService.issueCertificate(new SubjectData(keyPair1.getPublic(),builder.build(),"12345678945",new Date(),new Date()),
                new IssuerData(keyPair2.getPrivate(),builder.build()));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/create/{csrId}")
    public ResponseEntity<CertificateDTO> createCertificate(@PathVariable Long csrId, @RequestBody IssuerDataDTO issuerDataDTO) throws NoSuchAlgorithmException, NoSuchProviderException {
        return new ResponseEntity<>(certificateService.createCertificate(csrId, issuerDataDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/revoke/{id}")
    public ResponseEntity<Boolean> revokeCertificate(@PathVariable("id") Long id){
        if(certificateService.revokeCertificate(id)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/findAll")
    public ResponseEntity<List<CertificateDTO>> findAllCertificates(){
        return new ResponseEntity<>(certificateService.getAllCertificates(),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/validate/{alias}")
    public ResponseEntity<Boolean> validate(@PathVariable String alias){
        return new ResponseEntity<>(certificateService.validateCert(alias),HttpStatus.OK);
    }

}
