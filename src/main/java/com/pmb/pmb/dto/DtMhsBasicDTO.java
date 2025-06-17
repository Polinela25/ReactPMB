package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsBasicDTO {
    private Integer id;
    private String nama;
    private String nopendaftaran;
    private String sex;
    private String tmplahir;
    private String tgllahir;

    public DtMhsBasicDTO(Integer id, String nama, String nopendaftaran, String sex, String tmplahir, String tgllahir) {
        this.id = id;
        this.nama = nama;
        this.nopendaftaran = nopendaftaran;
        this.sex = sex;
        this.tmplahir = tmplahir;
        this.tgllahir = tgllahir;
    }
}