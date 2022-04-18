package com.adminapp.services.impl;

import com.adminapp.services.ICSRService;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.stereotype.Service;

@Service
public class CSRService implements ICSRService {
    @Override
    public void createCSR(byte[] csr) {
    }

    public static PKCS10CertificationRequest getCsr(byte[] csr) {
        return null;
    }
}
