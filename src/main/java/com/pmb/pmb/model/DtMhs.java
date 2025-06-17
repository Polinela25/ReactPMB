package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "dt_mhs")
@Getter
@Setter
@NoArgsConstructor
public class DtMhs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String nama;

    private String nopendaftaran;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String tmplahir;

    @Temporal(TemporalType.DATE)
    private Date tgllahir;

    @Enumerated(EnumType.STRING)
    private BidikmisiStatus bidikmisi;

    public enum Sex { MALE, FEMALE }
    public enum BidikmisiStatus { YES, NO }

    // All-args constructor
    public DtMhs(Integer id, Integer idcmhsbaru, String nama, String nopendaftaran, Sex sex, String tmplahir,
                 Date tgllahir, BidikmisiStatus bidikmisi) {
        this.id = id;
        this.idcmhsbaru = idcmhsbaru;
        this.nama = nama;
        this.nopendaftaran = nopendaftaran;
        this.sex = sex;
        this.tmplahir = tmplahir;
        this.tgllahir = tgllahir;
        this.bidikmisi = bidikmisi;
    }
}
