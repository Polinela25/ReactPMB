package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsAlamat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtMhsAlamatRepository extends JpaRepository<DtMhsAlamat, Integer> {
    Page<DtMhsAlamat> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    Page<DtMhsAlamat> findByNamalingkunganContainingIgnoreCase(String namalingkungan, Pageable pageable);
    List<DtMhsAlamat> findByAlamatemail(String alamatemail);
}