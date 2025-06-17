package com.pmb.pmb.controller;

import com.pmb.pmb.dto.DtMhsBasicDTO;
import com.pmb.pmb.model.DtMhs;
import com.pmb.pmb.repository.DtMhsRepository;
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
@RequestMapping("/api/dtmhs")
@CrossOrigin(origins = "*")
public class DtMhsController {

    @Autowired
    private DtMhsRepository repository;

    // GET all data with pagination and sorting
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        try {
            Sort.Order order = new Sort.Order(
                    sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    sort[0]);
            Pageable paging = PageRequest.of(page, size, Sort.by(order));
            Page<DtMhs> pageData = repository.findAll(paging);

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
    public ResponseEntity<List<DtMhs>> getByIdCmhsBaru(
            @RequestParam Integer idcmhsbaru,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhs> result = repository.findByIdcmhsbaru(idcmhsbaru, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by nama
    @GetMapping("/search/nama")
    public ResponseEntity<List<DtMhs>> searchByNama(
            @RequestParam String nama,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhs> result = repository.findByNamaContainingIgnoreCase(nama, paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET by nopendaftaran
    @GetMapping("/nopendaftaran")
    public ResponseEntity<List<DtMhs>> getByNoPendaftaran(@RequestParam String nopendaftaran) {
        List<DtMhs> result = repository.findByNopendaftaran(nopendaftaran);
        return ResponseEntity.ok(result);
    }

    // GET by bidikmisi
    @GetMapping("/bidikmisi")
    public ResponseEntity<List<DtMhs>> getByBidikmisi(
            @RequestParam String bidikmisi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DtMhs> result = repository.findByBidikmisi(DtMhs.BidikmisiStatus.valueOf(bidikmisi), paging);
        return ResponseEntity.ok(result.getContent());
    }

    // GET basic info by id
    @GetMapping("/basic/{id}")
    public ResponseEntity<DtMhsBasicDTO> getBasicInfoById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(data -> ResponseEntity.ok(new DtMhsBasicDTO(
                        data.getId(),
                        data.getNama(),
                        data.getNopendaftaran(),
                        data.getSex().toString(),
                        data.getTmplahir(),
                        data.getTgllahir())))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new DtMhsBasicDTO(null, null, null, null, null, "Data tidak ditemukan")));
    }
}