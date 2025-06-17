package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dt_mhs_alamat")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsAlamat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalamat")
    private int idalamat;

    @Column(name = "idcmhsbaru")
    private int idcmhsbaru;

    private String namaasli;
    private String namalingkungan;
    private String kdkelurahan;
    private String kdkabupatenkota;
    private String kdprovinsi;
    private String kodepos;
    private String notelepon;
    private String alamatemail;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PublishStatus publish;

    public enum Status { ZERO, VALID }
    public enum PublishStatus { T, S }

    // All-args constructor
    public DtMhsAlamat(int idalamat, int idcmhsbaru, String namaasli, String namalingkungan, String kdkelurahan,
                       String kdkabupatenkota, String kdprovinsi, String pos, String alamat, String email,
                       Status status, PublishStatus publish) {
        this.idalamat = idalamat;
        this.idcmhsbaru = idcmhsbaru;
        this.namaasli = namaasli;
        this.namalingkungan = namalingkungan;
        this.kdkelurahan = kdkelurahan;
        this.kdkabupatenkota = kdkabupatenkota;
        this.kdprovinsi = kdprovinsi;
        this.kodepos = pos;
        this.notelepon = alamat;
        this.alamatemail = email;
        this.status = status;
        this.publish = publish;
    }
}