package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dt_mhs_pilihan")
@Data
public class DtMhsPilihan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpilihan;

    private String idprodiy;
    private Integer pilihanke;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double spipendaftar;
    private Integer idcmhsbaru;

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }
}