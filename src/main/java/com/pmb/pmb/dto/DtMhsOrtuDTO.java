package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}