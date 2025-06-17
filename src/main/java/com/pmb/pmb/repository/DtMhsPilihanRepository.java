package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsPilihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DtMhsPilihanRepository extends JpaRepository<DtMhsPilihan, Integer> {
    Page<DtMhsPilihan> findByIdcmhsbaru(Integer idcmhsbaru, Pageable pageable);
    Page<DtMhsPilihan> findByIdprodiy(String idprodiy, Pageable pageable);
    Page<DtMhsPilihan> findByStatus(DtMhsPilihan.Status status, Pageable pageable);
}
