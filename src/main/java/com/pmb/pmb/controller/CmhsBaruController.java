package com.pmb.pmb.controller;

import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.dto.NamaTglLahirDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.CmhsBaru;
import com.pmb.pmb.repository.CmhsBaruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/cmhsbaru")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, maxAge = 3600, allowCredentials = "true")
public class CmhsBaruController {

    @Autowired
    private CmhsBaruRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-yyyy-dd HH:mm:ss");

    private void checkAuthorization(String operation) {
        // Replace with SecurityContextHolder.getContext().getAuthentication() for role-based checks
        boolean isAdmin = true;
        if (!isAdmin) {
            throw new ForbiddenException("Access denied: Insufficient permissions for " + operation);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idcmhsbaru,asc") String[] sort) {
        try {
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Page must be >= 0 and size must be > 0", LocalDateTime.now().format(formatter)));
            }
            if (sort.length != 2 || (!sort[1].equalsIgnoreCase("asc") && !sort[1].equalsIgnoreCase("desc"))) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Sort must be in format 'field,asc' or 'field,desc'", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch all CmhsBaru");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<CmhsBaru> pageCmhs = repository.findAll(paging);

            if (pageCmhs.isEmpty() && page > 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No data found for page " + page, LocalDateTime.now().format(formatter)));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", pageCmhs.getContent());
            response.put("currentPage", pageCmhs.getNumber());
            response.put("totalItems", pageCmhs.getTotalElements());
            response.put("totalPages", pageCmhs.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden", e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }

    @GetMapping("/nama-tgllahir/{idcmhsbaru}")
    public ResponseEntity<?> getNamaTglLahirById(@PathVariable int idcmhsbaru) {
        try {
            if (idcmhsbaru <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Idcmhsbaru must be a positive integer", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch nama and tgllahir by id");

            Optional<CmhsBaru> cmhsOpt = repository.findById(idcmhsbaru);
            if (cmhsOpt.isPresent()) {
                CmhsBaru cmhs = cmhsOpt.get();
                String nama = cmhs.getNamaasli();
                Date tgllahir = cmhs.getTgllahir();
                String tgllahirStr = tgllahir != null
                        ? new SimpleDateFormat("dd-MM-yyyy").format(tgllahir)
                        : "Tanggal lahir tidak tersedia";
                return ResponseEntity.ok(new NamaTglLahirDTO(nama, tgllahirStr));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No record found for idcmhsbaru: " + idcmhsbaru, LocalDateTime.now().format(formatter)));
            }
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden", e.getMessage(),
                    LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }
}