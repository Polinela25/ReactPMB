package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsAlamatDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhsAlamat;
import com.pmb.pmb.repository.DtMhsAlamatRepository;
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
@RequestMapping("/api/dtmhsalamat")
@CrossOrigin(origins = "*")
public class DtMhsAlamatController {

    @Autowired
    private DtMhsAlamatRepository repository;

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
            @RequestParam(defaultValue = "idalamat,asc") String[] sort) {
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
            checkAuthorization("fetch all DtMhsAlamat");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsAlamat> pageData = repository.findAll(paging);

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
            Page<DtMhsAlamat> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);

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

    // GET by namalingkungan
    @GetMapping("/search/namalingkungan")
    public ResponseEntity<?> searchByNamalingkungan(
            @RequestParam String namalingkungan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate inputs
            if (namalingkungan == null || namalingkungan.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Namalingkungan parameter is required and cannot be empty",
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
            checkAuthorization("search by namalingkungan");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsAlamat> result = repository.findByNamalingkunganContainingIgnoreCase(namalingkungan, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for namalingkungan: " + namalingkungan,
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

    // GET by alamatemail
    @GetMapping("/alamatemail")
    public ResponseEntity<?> getByAlamatemail(@RequestParam String alamatemail) {
        try {
            // Validate inputs
            if (alamatemail == null || alamatemail.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Alamatemail parameter is required and cannot be empty",
                        LocalDateTime.now().format(formatter)));
            }
            if (!alamatemail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Invalid email format",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch by alamatemail");

            List<DtMhsAlamat> result = repository.findByAlamatemail(alamatemail);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No records found for alamatemail: " + alamatemail,
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
    @GetMapping("/basic/{idalamat}")
    public ResponseEntity<?> getBasicInfoById(@PathVariable Integer idalamat) {
        try {
            // Validate inputs
            if (idalamat == null || idalamat <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Idalamat must be a positive integer",
                        LocalDateTime.now().format(formatter)));
            }

            // Check authorization
            checkAuthorization("fetch basic info by id");

            Optional<DtMhsAlamat> dataOpt = repository.findById(idalamat);
            if (dataOpt.isPresent()) {
                DtMhsAlamat data = dataOpt.get();
                return ResponseEntity.ok(new DtMhsAlamatDTO(
                        data.getIdalamat(),
                        data.getNamalingkungan(),
                        data.getKdkelurahan(),
                        data.getAlamatemail(),
                        data.getNotelepon()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        "No record found for idalamat: " + idalamat,
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
