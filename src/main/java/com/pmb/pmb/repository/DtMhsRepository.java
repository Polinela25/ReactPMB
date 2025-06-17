package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtMhsRepository extends JpaRepository<DtMhs, Integer> {
    Page<DtMhs> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    List<DtMhs> findByNamaContainingIgnoreCase(String nama);
    Page<DtMhs> findByNamaContainingIgnoreCase(String nama, Pageable pageable);
    List<DtMhs> findByNopendaftaran(String nopendaftaran);
    Page<DtMhs> findByBidikmisi(DtMhs.BidikmisiStatus bidikmisi, Pageable pageable);
}