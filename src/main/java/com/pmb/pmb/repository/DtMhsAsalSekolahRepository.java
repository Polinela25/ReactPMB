package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsAsalSekolah;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtMhsAsalSekolahRepository extends JpaRepository<DtMhsAsalSekolah, Integer> {
    Page<DtMhsAsalSekolah> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    List<DtMhsAsalSekolah> findByNisn(String nisn);
    Page<DtMhsAsalSekolah> findByNamasekolahContainingIgnoreCase(String namasekolah, Pageable pageable);
    Page<DtMhsAsalSekolah> findByThnlulus(String thnlulus, Pageable pageable);
}