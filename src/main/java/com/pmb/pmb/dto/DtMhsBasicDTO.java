package com.pmb.pmb.dto;

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

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNopendaftaran() {
        return nopendaftaran;
    }

    public String getSex() {
        return sex;
    }

    public String getTmplahir() {
        return tmplahir;
    }

    public String getTgllahir() {
        return tgllahir;
    }
}