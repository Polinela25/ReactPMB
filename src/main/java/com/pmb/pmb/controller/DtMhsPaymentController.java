package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsPaymentDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhsPayment;
import com.pmb.pmb.repository.DtMhsPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dtmhspayment")
@CrossOrigin(origins = "*")
public class DtMhsPaymentController {

    @Autowired
    private DtMhsPaymentRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Authorization check
    private void checkAuthorization(String operation) {
        // Temporary for testing
        boolean isAdmin = true;
        if (!isAdmin) {
            throw new ForbiddenException("Access denied: Insufficient permissions for " + operation);
        }
    }

    // GET by trxId
    @GetMapping("/trx-id")
    public ResponseEntity<?> getByTrxId(@RequestParam String trxId) {
        try {
            // Validate inputs
            if (trxId == null || trxId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "TrxId parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by trxId");

            List<DtMhsPayment> result = repository.findByTrxId(trxId);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for trxId: " + trxId,
                        LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(),
                    "Forbidden",
                    e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        }
    }

    // GET by idcmhsbaru
    @GetMapping("/idcmhsbaru")
    public ResponseEntity<?> getByIdCmhsBaru(@RequestParam Integer idcmhsbaru) {
        try {
            // Validate inputs
            if (idcmhsbaru == null || idcmhsbaru <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Idcmhsbaru must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by idcmhsbaru");

            List<DtMhsPayment> result = repository.findByIdcmhsbaru(idcmhsbaru);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for idcmhsbaru: " + idcmhsbaru,
                        LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(),
                    "Forbidden",
                    e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        }
    }

    // GET basic info by id
    @GetMapping("/basic/{id}")
    public ResponseEntity<?> getBasicInfoById(@PathVariable Integer id) {
        try {
            // Validate inputs
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Id must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch basic info by id");

            Optional<DtMhsPayment> dataOpt = repository.findById(id);
            if (dataOpt.isPresent()) {
                DtMhsPayment data = dataOpt.get();
                String lastUpdateStr = data.getLastUpdate() != null
                        ? LocalDateTime.ofInstant(data.getLastUpdate().toInstant(), ZoneId.systemDefault())
                        .format(formatter)
                        : "Tanggal update tidak tersedia";
                return ResponseEntity.ok(new DtMhsPaymentDTO(
                        data.getId(),
                        data.getTrxId(),
                        data.getAmount(),
                        data.getStatus().toString(),
                        lastUpdateStr));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No record found for id: " + id,
                        LocalDateTime.now().format(formatter)));
            }
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(),
                    "Forbidden",
                    e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        }
    }
}
