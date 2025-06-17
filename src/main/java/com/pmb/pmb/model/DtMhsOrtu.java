package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dt_mhs_ortu")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsOrtu {

    @Id
    @Column(name = "idortu")
    private Integer idortu;

    @Column(name = "idjnortu")
    private Integer idjnortu;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String nama;
    private String tmplahir;
    private String tgllahir;
    private Integer idpekerjaan;
    private Integer penghasilan_tambahan;
    private String alamatkerja;
    private Integer idpenghasilan;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String namalingkungan;
    private String kdkabupatenkota;
    private String kdprovinsi;
    private String kodepos;
    private String notelepon1;
    private String notelepon2;
    private String email;

    @Enumerated(EnumType.STRING)
    private PublishStatus publish;

    private String nik;

    @Enumerated(EnumType.STRING)
    private SdataStatus sdata;

    public enum Status { ZERO, ONE }
    public enum PublishStatus { T, F }
    public enum SdataStatus { ZERO, ONE, TWO, THREE }
}