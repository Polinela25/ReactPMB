package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NamaTglLahirDTO {
    private String nama;
    private String tgllahir;

    public NamaTglLahirDTO(String nama, String tgllahir) {
        this.nama = nama;
        this.tgllahir = tgllahir;
    }
}