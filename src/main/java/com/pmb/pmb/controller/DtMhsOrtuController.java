package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsOrtuDTO;
import com.pmb.pmb.model.DtMhsOrtu;
import com.pmb.pmb.repository.DtMhsOrtuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dtmhsortu")
@CrossOrigin(origins = "*")
public class DtMhsOrtuController {

    @Autowired
    private DtMhsOrtuRepository repository;

    // GET all data with pagination and sorting
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idortu,asc") String[] sort) {
        try {
            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsOrtu> pageData = repository.findAll(paging);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pageData.getContent());
            response.put("currentPage", pageData.getNumber());
            response.put("totalItems", pageData.getTotalElements());
            response.put("totalPages", pageData.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET by idcmhsbaru
    @GetMapping("/idcmhsbaru")
    public ResponseEntity<List<DtMhsOrtu>> getByIdCmhsBaru(
            @RequestParam Integer idcmhsbaru,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhsOrtu> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by nama
    @GetMapping("/search/nama")
    public ResponseEntity<List<DtMhsOrtu>> searchByNama(
            @RequestParam String nama,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhsOrtu> result = repository.findByNamaContainingIgnoreCase(nama, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by email
    @GetMapping("/email")
    public ResponseEntity<List<DtMhsOrtu>> getByEmail(@RequestParam String email) {
        List<DtMhsOrtu> result = repository.findByEmail(email);
        return ResponseEntity.ok(result);
    }

    // GET basic info by id
    @GetMapping("/basic/{idortu}")
    public ResponseEntity<DtMhsOrtuDTO> getBasicInfoById(@PathVariable Integer idortu) {
        return repository.findById(idortu)
                .map(data -> ResponseEntity.ok(new DtMhsOrtuDTO(
                        data.getIdortu(),
                        data.getNama(),
                        data.getTgllahir(),
                        data.getEmail(),
                        data.getNotelepon1())))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new DtMhsOrtuDTO(null, null, null, null, "Data tidak ditemukan")));
    }
}