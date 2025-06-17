package com.pmb.pmb.dto;

public class DtMhsPilihanDTO {
    private Integer idpilihan;
    private String idprodiy;
    private Integer pilihanke;
    private String status;
    private Integer spipendaftar;

    public DtMhsPilihanDTO(Integer idpilihan, String idprodiy, Integer pilihanke, String status, Integer spipendaftar) {
        this.idpilihan = idpilihan;
        this.idprodiy = idprodiy;
        this.pilihanke = pilihanke;
        this.status = status;
        this.spipendaftar = spipendaftar;
    }

    public Integer getIdpilihan() {
        return idpilihan;
    }

    public String getIdprodiy() {
        return idprodiy;
    }

    public Integer getPilihanke() {
        return pilihanke;
    }

    public String getStatus() {
        return status;
    }

    public Integer getSpipendaftar() {
        return spipendaftar;
    }
}