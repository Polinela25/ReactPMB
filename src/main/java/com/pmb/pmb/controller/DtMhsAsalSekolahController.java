package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsAsalSekolahDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhsAsalSekolah;
import com.pmb.pmb.repository.DtMhsAsalSekolahRepository;
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
@RequestMapping("/api/dtmhsasalsekolah")
@CrossOrigin(origins = "*")
public class DtMhsAsalSekolahController {

    @Autowired
    private DtMhsAsalSekolahRepository repository;

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
            @RequestParam(defaultValue = "idsekolahasal,asc") String[] sort) {
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
            checkAuthorization("fetch all DtMhsAsalSekolah");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsAsalSekolah> pageData = repository.findAll(paging);

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
            Page<DtMhsAsalSekolah> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);

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

    // GET by nisn
    @GetMapping("/nisn")
    public ResponseEntity<?> getByNisn(@RequestParam String nisn) {
        try {
            // Validate inputs
            if (nisn == null || nisn.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Nisn parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by nisn");

            List<DtMhsAsalSekolah> result = repository.findByNisn(nisn);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for nisn: " + nisn,
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

    // GET by namasekolah
    @GetMapping("/search/namasekolah")
    public ResponseEntity<?> searchByNamasekolah(
            @RequestParam String namasekolah,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (namasekolah == null || namasekolah.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Namasekolah parameter is required and cannot be empty",
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
            checkAuthorization("search by namasekolah");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsAsalSekolah> result = repository.findByNamasekolahContainingIgnoreCase(namasekolah, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for namasekolah: " + namasekolah,
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

    // GET by thnlulus
    @GetMapping("/thnlulus")
    public ResponseEntity<?> getByThnlulus(
            @RequestParam String thnlulus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (thnlulus == null || !thnlulus.matches("\\d{4}")) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Thnlulus must be a valid 4-digit year",
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
            checkAuthorization("fetch by thnlulus");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsAsalSekolah> result = repository.findByThnlulus(thnlulus, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for thnlulus: " + thnlulus,
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
    @GetMapping("/basic/{idsekolahasal}")
    public ResponseEntity<?> getBasicInfoById(@PathVariable Integer idsekolahasal) {
        try {
            // Validate inputs
            if (idsekolahasal == null || idsekolahasal <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Idsekolahasal must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch basic info by id");

            Optional<DtMhsAsalSekolah> dataOpt = repository.findById(idsekolahasal);
            if (dataOpt.isPresent()) {
                DtMhsAsalSekolah data = dataOpt.get();
                return ResponseEntity.ok(new DtMhsAsalSekolahDTO(
                        data.getIdsekolahasal(),
                        data.getNisn(),
                        data.getNamasekolah(),
                        data.getThnlulus(),
                        data.getNoijazah()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No record found for idsekolahasal: " + idsekolahasal,
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
