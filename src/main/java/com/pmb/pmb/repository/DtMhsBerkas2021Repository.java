package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsBerkas2021;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DtMhsBerkas2021Repository extends JpaRepository<DtMhsBerkas2021, Integer> {
    Page<DtMhsBerkas2021> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    Page<DtMhsBerkas2021> findByPhotoStatus(DtMhsBerkas2021.Status photoStatus, Pageable pageable);
}