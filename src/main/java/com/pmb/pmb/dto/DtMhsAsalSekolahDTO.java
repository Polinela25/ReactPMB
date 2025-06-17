package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsAsalSekolahDTO {
    private Integer idsekolahasal;
    private String nisn;
    private String namasekolah;
    private String thnlulus;
    private String noijazah;

    public DtMhsAsalSekolahDTO(Integer idsekolahasal, String nisn, String namasekolah, String thnlulus, String noijazah) {
        this.idsekolahasal = idsekolahasal;
        this.nisn = nisn;
        this.namasekolah = namasekolah;
        this.thnlulus = thnlulus;
        this.noijazah = noijazah;
    }
}