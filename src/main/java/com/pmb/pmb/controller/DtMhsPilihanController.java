package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsPilihanDTO;
import com.pmb.pmb.dto.ErrorResponseDTO;
import com.pmb.pmb.exception.ForbiddenException;
import com.pmb.pmb.model.DtMhsPilihan;
import com.pmb.pmb.repository.DtMhsPilihanRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/dtmhspilihan")
@CrossOrigin(origins = "*")
@Tag(name = "DtMhsPilihan", description = "API for managing student program choices")
public class DtMhsPilihanController {

    @Autowired
    private DtMhsPilihanRepository repository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void checkAuthorization(String operation) {
        boolean isAdmin = true; // Replace with actual security check
        if (!isAdmin) {
            throw new ForbiddenException("Access denied: Insufficient permissions for " + operation);
        }
    }

    @GetMapping
    @Operation(summary = "Get all student choices with pagination and sorting",
            description = "Retrieve a paginated list of student program choices, sorted by specified field.",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
            })
    public ResponseEntity<?> getAllData(
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field and direction (e.g., 'idpilihan,asc')", example = "idpilihan,asc") @RequestParam(defaultValue = "idpilihan,asc") String[] sort) {
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

            checkAuthorization("fetch all DtMhsPilihan");

            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsPilihan> pageData = repository.findAll(paging);

            if (pageData.isEmpty() && page > 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No data found for page " + page, LocalDateTime.now().format(formatter)));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", pageData.getContent());
            response.put("currentPage", pageData.getNumber());
            response.put("totalItems", pageData.getTotalElements());
            response.put("totalPages", pageData.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden",
                    e.getMessage(), LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }

    @GetMapping("/idcmhsbaru")
    @Operation(summary = "Get choices by student ID",
            description = "Retrieve a paginated list of choices for a specific student ID.",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No records found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
            })
    public ResponseEntity<?> getByIdCmhsBaru(
            @Parameter(description = "Student ID", example = "101") @RequestParam Integer idcmhsbaru,
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        try {
            if (idcmhsbaru == null || idcmhsbaru <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Idcmhsbaru must be a positive integer", LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Page must be >= 0 and size must be > 0", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch by idcmhsbaru");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsPilihan> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No records found for idcmhsbaru: " + idcmhsbaru, LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden",
                    e.getMessage(), LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }

    @GetMapping("/idprodiy")
    @Operation(summary = "Get choices by program ID",
            description = "Retrieve a paginated list of choices for a specific program ID.",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No records found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
            })
    public ResponseEntity<?> getByIdProdiy(
            @Parameter(description = "Program ID", example = "PROG001") @RequestParam String idprodiy,
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        try {
            if (idprodiy == null || idprodiy.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Idprodiy parameter is required and cannot be empty", LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Page must be >= 0 and size must be > 0", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch by idprodiy");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsPilihan> result = repository.findByIdprodiy(idprodiy, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No records found for idprodiy: " + idprodiy, LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden",
                    e.getMessage(), LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Get choices by status",
            description = "Retrieve a paginated list of choices by status (e.g., PENDING, ACCEPTED).",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No records found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
            })
    public ResponseEntity<?> getByStatus(
            @Parameter(description = "Status (e.g., PENDING, ACCEPTED, REJECTED)", example = "PENDING") @RequestParam String status,
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        try {
            if (status == null || status.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Status parameter is required and cannot be empty", LocalDateTime.now().format(formatter)));
            }
            DtMhsPilihan.Status statusEnum;
            try {
                statusEnum = DtMhsPilihan.Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Invalid status. Valid values are: PENDING, ACCEPTED, REJECTED", LocalDateTime.now().format(formatter)));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Page must be >= 0 and size must be > 0", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch by status");

            Pageable paging = PageRequest.of(page, size);
            Page<DtMhsPilihan> result = repository.findByStatus(statusEnum, paging);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No records found for status: " + status, LocalDateTime.now().format(formatter)));
            }

            return ResponseEntity.ok(result.getContent());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden",
                    e.getMessage(), LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }

    @GetMapping("/basic/{idpilihan}")
    @Operation(summary = "Get basic choice info by ID",
            description = "Retrieve basic information for a specific choice by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = DtMhsPilihanDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Record not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
            })
    public ResponseEntity<?> getBasicInfoById(
            @Parameter(description = "Choice ID", example = "1") @PathVariable Integer idpilihan) {
        try {
            if (idpilihan == null || idpilihan <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Idpilihan must be a positive integer", LocalDateTime.now().format(formatter)));
            }

            checkAuthorization("fetch basic info by id");

            Optional<DtMhsPilihan> dataOpt = repository.findById(idpilihan);
            if (dataOpt.isPresent()) {
                DtMhsPilihan data = dataOpt.get();
                return ResponseEntity.ok(new DtMhsPilihanDTO(
                        data.getIdpilihan(),
                        data.getIdprodiy(),
                        data.getPilihanke(),
                        data.getStatus().toString(),
                        data.getSpipendaftar()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(), "Not Found",
                        "No record found for idpilihan: " + idpilihan, LocalDateTime.now().format(formatter)));
            }
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(
                    HttpStatus.FORBIDDEN.value(), "Forbidden",
                    e.getMessage(), LocalDateTime.now().format(formatter)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(), LocalDateTime.now().format(formatter)));
        }
    }
}
