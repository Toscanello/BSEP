package com.adminapp.repository;

import com.adminapp.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findBySerialNumber(Long serialNumber);

    @Query("select c from Certificate c join fetch c.parentCertificate cp where cp.id = :id")
    List<Certificate> findByIssuer(Long id);

    @Query("select c from Certificate c where c.isCA = true")
    Certificate findCA();
}
