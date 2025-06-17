package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsPaymentDTO;
import com.pmb.pmb.model.DtMhsPayment;
import com.pmb.pmb.repository.DtMhsPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api/dtmhspayment")
@CrossOrigin(origins = "*")
public class DtMhsPaymentController {

    @Autowired
    private DtMhsPaymentRepository repository;

    // GET by trxId
    @GetMapping("/trx-id")
    public ResponseEntity<List<DtMhsPayment>> getByTrxId(@RequestParam String trxId) {
        List<DtMhsPayment> result = repository.findByTrxId(trxId);
        return ResponseEntity.ok(result);
    }

    // GET by idcmhsbaru
    @GetMapping("/idcmhsbaru")
    public ResponseEntity<List<DtMhsPayment>> getByIdCmhsBaru(@RequestParam Integer idcmhsbaru) {
        List<DtMhsPayment> result = repository.findByIdcmhsbaru(idcmhsbaru);
        return ResponseEntity.ok(result);
    }

    // GET basic info by id
    @GetMapping("/basic/{id}")
    public ResponseEntity<DtMhsPaymentDTO> getBasicInfoById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(data -> {
                    String lastUpdateStr = data.getLastUpdate() != null
                            ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getLastUpdate())
                            : "Tanggal update tidak tersedia";
                    return ResponseEntity.ok(new DtMhsPaymentDTO(
                            data.getId(),
                            data.getTrxId(),
                            data.getAmount(),
                            data.getStatus().toString(),
                            lastUpdateStr));
                })
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new DtMhsPaymentDTO(null, null, null, null, "Data tidak ditemukan")));
    }
}
