package com.adminapp.services;

import com.adminapp.models.Csr;
import com.adminapp.models.dto.CsrDTO;

import java.io.IOException;
import java.util.List;

public interface ICSRService {

    public List<Csr> getAll();
    public Csr get(Long id);
    public void createCSR(CsrDTO csr) throws IOException;

}
