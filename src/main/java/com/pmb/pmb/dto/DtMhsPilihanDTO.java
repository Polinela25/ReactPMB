package com.pmb.pmb.dto;

import lombok.Data;

@Data
public class DtMhsPilihanDTO {
    private Integer idpilihan;
    private String idprodiy;
    private Integer pilihanke;
    private String status;
    private Double spipendaftar;

    public DtMhsPilihanDTO(Integer idpilihan, String idprodiy, Integer pilihanke, String status, Double spipendaftar) {
        this.idpilihan = idpilihan;
        this.idprodiy = idprodiy;
        this.pilihanke = pilihanke;
        this.status = status;
        this.spipendaftar = spipendaftar;
    }
}