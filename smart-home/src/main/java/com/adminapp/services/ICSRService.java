package com.adminapp.services;

import com.adminapp.models.Csr;
import com.adminapp.models.dto.CsrDTO;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.IOException;
import java.util.List;

public interface ICSRService {

    public List<Csr> getAll();
    public Csr get(Long id);
    public String createCSR(CsrDTO csr) throws IOException, OperatorCreationException;

}
