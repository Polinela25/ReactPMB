package com.pmb.pmb.repository;

import com.pmb.pmb.model.CmhsBaru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CmhsBaruRepository extends JpaRepository<CmhsBaru, Integer> {
    List<CmhsBaru> findByNamaasliContainingIgnoreCase(String namaasli);
    Page<CmhsBaru> findByNamaasliContainingIgnoreCase(String namaasli, Pageable pageable);
    List<CmhsBaru> findByNamaasliContainingIgnoreCaseAndEmail(String namaasli, String email);
    Page<CmhsBaru> findByTahun(String tahun, Pageable pageable);
    List<CmhsBaru> findByEmail(String email);
    Page<CmhsBaru> findByBeasiswa(String beasiswa, Pageable pageable);
    Page<CmhsBaru> findByStatusbayar(String statusbayar, Pageable pageable);
    Page<CmhsBaru> findByKdprodi(String kdprodi, Pageable pageable);
    List<CmhsBaru> findByVa(String va);
    Page<CmhsBaru> findByIdjalurpmb(Integer idjalurpmb, Pageable pageable);
}