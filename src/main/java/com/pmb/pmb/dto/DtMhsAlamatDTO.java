package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsAlamatDTO {
    private Integer idalamat;
    private String namalingkungan;
    private String kdkelurahan;
    private String alamatemail;
    private String notelepon;

    public DtMhsAlamatDTO(Integer idalamat, String namalingkungan, String kdkelurahan, String alamatemail, String notelepon) {
        this.idalamat = idalamat;
        this.namalingkungan = namalingkungan;
        this.kdkelurahan = kdkelurahan;
        this.alamatemail = alamatemail;
        this.notelepon = notelepon;
    }
}