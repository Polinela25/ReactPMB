package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dt_mhs_asal_sekolah")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsAsalSekolah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsekolahasal")
    private Integer idsekolahasal;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String nisn;

    private String namasekolah;

    private String thnlulus;

    private String noijazah;

    // All-args constructor
    public DtMhsAsalSekolah(Integer idsekolahasal, Integer idcmhsbaru, String nisn, String namasekolah, String thnlulus, String noijazah) {
        this.idsekolahasal = idsekolahasal;
        this.idcmhsbaru = idcmhsbaru;
        this.nisn = nisn;
        this.namasekolah = namasekolah;
        this.thnlulus = thnlulus;
        this.noijazah = noijazah;
    }
}