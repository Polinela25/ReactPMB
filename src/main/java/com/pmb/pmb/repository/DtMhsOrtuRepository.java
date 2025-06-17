package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsOrtu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtMhsOrtuRepository extends JpaRepository<DtMhsOrtu, Integer> {
    Page<DtMhsOrtu> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    Page<DtMhsOrtu> findByNamaContainingIgnoreCase(String nama, Pageable pageable);
    List<DtMhsOrtu> findByEmail(String email);
}
