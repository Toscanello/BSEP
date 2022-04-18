package com.adminapp.repository;

import com.adminapp.models.Csr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSRRepository extends JpaRepository<Csr, Long> {
}
