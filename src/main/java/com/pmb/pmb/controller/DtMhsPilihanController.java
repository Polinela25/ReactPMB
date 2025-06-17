package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsPilihanDTO;
import com.pmb.pmb.model.DtMhsPilihan;
import com.pmb.pmb.repository.DtMhsPilihanRepository;
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
@RequestMapping("/api/dtmhspilihan")
@CrossOrigin(origins = "*")
public class DtMhsPilihanController {

    @Autowired
    private DtMhsPilihanRepository repository;

    // GET all data with pagination and sorting
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idpilihan,asc") String[] sort) {
        try {
            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhsPilihan> pageData = repository.findAll(paging);

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
    public ResponseEntity<List<DtMhsPilihan>> getByIdCmhsBaru(
            @RequestParam Integer idcmhsbaru,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhsPilihan> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by idprodiy
    @GetMapping("/idprodiy")
    public ResponseEntity<List<DtMhsPilihan>> getByIdProdiy(
            @RequestParam String idprodiy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhsPilihan> result = repository.findByIdprodiy(idprodiy, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by status
    @GetMapping("/status")
    public ResponseEntity<List<DtMhsPilihan>> getByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhsPilihan> result = repository.findByStatus(DtMhsPilihan.Status.valueOf(status), paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET basic info by id
    @GetMapping("/basic/{idpilihan}")
    public ResponseEntity<DtMhsPilihanDTO> getBasicInfoById(@PathVariable Integer idpilihan) {
        return repository.findById(idpilihan)
                .map(data -> ResponseEntity.ok(new DtMhsPilihanDTO(
                        data.getIdpilihan(),
                        data.getIdprodiy(),
                        data.getPilihanke(),
                        data.getStatus().toString(),
                        data.getSpipendaftar())))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new DtMhsPilihanDTO(null, null, null, null, null)));
    }
}