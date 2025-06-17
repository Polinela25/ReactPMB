package com.pmb.pmb.dto;

public class DtMhsOrtuDTO {
    private Integer idortu;
    private String nama;
    private String tgllahir;
    private String email;
    private String notelepon1;

    public DtMhsOrtuDTO(Integer idortu, String nama, String tgllahir, String email, String notelepon1) {
        this.idortu = idortu;
        this.nama = nama;
        this.tgllahir = tgllahir;
        this.email = email;
        this.notelepon1 = notelepon1;
    }

    public Integer getIdortu() {
        return idortu;
    }

    public String getNama() {
        return nama;
    }

    public String getTgllahir() {
        return tgllahir;
    }

    public String getEmail() {
        return email;
    }

    public String getNotelepon1() {
        return notelepon1;
    }
}