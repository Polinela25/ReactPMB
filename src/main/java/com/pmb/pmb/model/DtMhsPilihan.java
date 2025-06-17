package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dt_mhs_pilihan")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsPilihan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpilihan")
    private Integer idpilihan;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String idprodiy;

    private Integer pilihanke;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double spipendaftar;

    public enum Status { PENDING, ACCEPTED, REJECTED }

    // All-args constructor
    public DtMhsPilihan(Integer idpilihan, Integer idcmhsbaru, String idprodiy, Integer pilihanke, Status status, Double spipendaftar) {
        this.idpilihan = idpilihan;
        this.idcmhsbaru = idcmhsbaru;
        this.idprodiy = idprodiy;
        this.pilihanke = pilihanke;
        this.status = status;
        this.spipendaftar = spipendaftar;
    }
}
