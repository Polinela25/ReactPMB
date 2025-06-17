package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsBerkas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DtMhsBerkasRepository extends JpaRepository<DtMhsBerkas, Integer> {
    Page<DtMhsBerkas> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    Page<DtMhsBerkas> findByKtpStatus(DtMhsBerkas.Status ktpStatus, Pageable pageable);
    Page<DtMhsBerkas> findByIjazahStatus(DtMhsBerkas.Status ijazahStatus, Pageable pageable);
    Page<DtMhsBerkas> findByAkteStatus(DtMhsBerkas.Status akteStatus, Pageable pageable);
    Page<DtMhsBerkas> findByKskunStatus(DtMhsBerkas.Status kskunStatus, Pageable pageable);
}