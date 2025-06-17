package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "dt_mhs_ortu")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsOrtu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idortu")
    private Integer idortu;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String nama;

    @Temporal(TemporalType.DATE)
    private Date tgllahir;

    private String email;

    private String notelepon1;

    // All-args constructor
    public DtMhsOrtu(Integer idortu, Integer idcmhsbaru, String nama, Date tgllahir, String email, String notelepon1) {
        this.idortu = idortu;
        this.idcmhsbaru = idcmhsbaru;
        this.nama = nama;
        this.tgllahir = tgllahir;
        this.email = email;
        this.notelepon1 = notelepon1;
    }
}
