package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsOrtuDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhsOrtu;
import com.pmb.pmb.repository.DtMhsOrtuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/dtmhsortu")
@CrossOrigin(origins = "*")
public class DtMhsOrtuController {

    @Autowired
    private DtMhsOrtuRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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
            @RequestParam(defaultValue = "idortu,asc") String[] sort) {
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
            checkAuthorization("fetch all DtMhsOrtu");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsOrtu> pageData = repository.findAll(paging);

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
            Page<DtMhsOrtu> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);

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
            Page<DtMhsOrtu> result = repository.findByNamaContainingIgnoreCase(nama, paging);

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

    // GET by email
    @GetMapping("/email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        try {
            // Validate inputs
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Email parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Invalid email format",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by email");

            List<DtMhsOrtu> result = repository.findByEmail(email);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for email: " + email,
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
    @GetMapping("/basic/{idortu}")
    public ResponseEntity<?> getBasicInfoById(@PathVariable Integer idortu) {
        try {
            // Validate inputs
            if (idortu == null || idortu <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Idortu must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch basic info by id");

            Optional<DtMhsOrtu> dataOpt = repository.findById(idortu);
            if (dataOpt.isPresent()) {
                DtMhsOrtu data = dataOpt.get();
                String tgllahirStr = data.getTgllahir() != null
                        ? LocalDateTime.ofInstant(data.getTgllahir().toInstant(), ZoneId.systemDefault())
                        .format(dateFormatter)
                        : null;
                return ResponseEntity.ok(new DtMhsOrtuDTO(
                        data.getIdortu(),
                        data.getNama(),
                        tgllahirStr,
                        data.getEmail(),
                        data.getNotelepon1()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No record found for idortu: " + idortu,
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
