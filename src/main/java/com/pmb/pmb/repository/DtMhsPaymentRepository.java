package com.pmb.pmb.repository;

import com.pmb.pmb.model.DtMhsPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtMhsPaymentRepository extends JpaRepository<DtMhsPayment, Integer> {
    List<DtMhsPayment> findByTrxId(String trxId);
    List<DtMhsPayment> findByIdcmhsbaru(Integer idcmhsbaru);
}