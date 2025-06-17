package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsBerkas2021DTO {
    private Integer id;
    private String ktp_file;
    private String ktp_status;
    private String photo_file;
    private String photo_status;
    private String last_update;

    public DtMhsBerkas2021DTO(Integer id, String ktp_file, String ktp_status, String photo_file, String photo_status, String last_update) {
        this.id = id;
        this.ktp_file = ktp_file;
        this.ktp_status = ktp_status;
        this.photo_file = photo_file;
        this.photo_status = photo_status;
        this.last_update = last_update;
    }
}