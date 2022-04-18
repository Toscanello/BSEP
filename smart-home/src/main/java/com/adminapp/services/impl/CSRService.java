package com.adminapp.services.impl;

import com.adminapp.services.ICSRService;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

public class CSRService implements ICSRService {
    @Override
    public void createCSR(byte[] csr) {
        PKCS10CertificationRequest certificationRequest =
    }

    public static PKCS10CertificationRequest getCsr(byte[] csr) {

    }
}
