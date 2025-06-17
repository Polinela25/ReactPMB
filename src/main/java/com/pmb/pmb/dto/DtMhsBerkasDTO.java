package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsBerkasDTO {
    private Integer id;
    private String ktp_file;
    private String ktpStatus;
    private String kskun_file;
    private String kskunStatus;
    private String lastUpdate;

    public DtMhsBerkasDTO(Integer id, String ktp_file, String ktpStatus, String kskun_file, String kskunStatus, String lastUpdate) {
        this.id = id;
        this.ktp_file = ktp_file;
        this.ktpStatus = ktpStatus;
        this.kskun_file = kskun_file;
        this.kskunStatus = kskunStatus;
        this.lastUpdate = lastUpdate;
    }
}