package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsBasicDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhs;
import com.pmb.pmb.repository.DtMhsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dtmhs")
@CrossOrigin(origins = "*")
public class DtMhsController {

    @Autowired
    private DtMhsRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Authorization check
    private void checkAuthorization(String operation) {
        // Temporary for testing
        boolean isAdmin = true;
        if (!isAdmin) {
            throw new ForbiddenException("Access denied: Insufficient permissions for " + operation);
        }
    }

    // GET all data with pagination and sorting
    @GetMapping
    public ResponseEntity<?> getAllData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        try {
            // Validate inputs
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Page must be >= 0 and size must be > 0",
                        LocalDateTime.now().format(formatter)));
            }
            if (sort.length != 2 || (!sort[1].equalsIgnoreCase("asc") && !sort[1].equalsIgnoreCase("desc"))) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Sort must be in format 'field,asc' or 'field,desc'",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch all DtMhs");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhs> pageData = repository.findAll(paging);

            if (pageData.isEmpty() && page > 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No data found for page " + page,
                        LocalDateTime.now().format(formatter)));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", pageData.getContent());
            response.put("currentPage", pageData.getNumber());
            response.put("totalItems", pageData.getTotalElements());
            response.put("totalPages", pageData.getTotalPages());

            return ResponseEntity.ok(response);
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
    public ResponseEntity<?> getByIdCmhsBaru(
            @RequestParam Integer idcmhsbaru,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (idcmhsbaru == null || idcmhsbaru <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Idcmhsbaru must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Page must be >= 0 and size must be > 0",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by idcmhsbaru");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhs> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for idcmhsbaru: " + idcmhsbaru,
                        LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
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

    // GET by nama
    @GetMapping("/search/nama")
    public ResponseEntity<?> searchByNama(
            @RequestParam String nama,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (nama == null || nama.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Nama parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Page must be >= 0 and size must be > 0",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("search by nama");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhs> result = repository.findByNamaContainingIgnoreCase(nama, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for nama: " + nama,
                        LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
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

    // GET by nopendaftaran
    @GetMapping("/nopendaftaran")
    public ResponseEntity<?> getByNoPendaftaran(@RequestParam String nopendaftaran) {
        try {
            // Validate inputs
            if (nopendaftaran == null || nopendaftaran.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Nopendaftaran parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by nopendaftaran");

            List<DtMhs> result = repository.findByNopendaftaran(nopendaftaran);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for nopendaftaran: " + nopendaftaran,
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

    // GET by bidikmisi
    @GetMapping("/bidikmisi")
    public ResponseEntity<?> getByBidikmisi(
            @RequestParam String bidikmisi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (bidikmisi == null || bidikmisi.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Bidikmisi parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }
            DtMhs.BidikmisiStatus status;
            try {
                status = DtMhs.BidikmisiStatus.valueOf(bidikmisi.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Invalid bidikmisi status. Valid values are: YES, NO",
                        LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Page must be >= 0 and size must be > 0",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by bidikmisi");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhs> result = repository.findByBidikmisi(status, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for bidikmisi: " + bidikmisi,
                        LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
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

            Optional<DtMhs> dataOpt = repository.findById(id);
            if (dataOpt.isPresent()) {
                DtMhs data = dataOpt.get();
                return ResponseEntity.ok(new DtMhsBasicDTO(
                        data.getId(),
                        data.getNama(),
                        data.getNopendaftaran(),
                        data.getSex().toString(),
                        data.getTmplahir(),
                        data.getTgllahir() != null ? data.getTgllahir().toString() : null));
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
