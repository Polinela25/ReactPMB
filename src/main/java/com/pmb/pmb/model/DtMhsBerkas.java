package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "dt_mhs_berkas")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsBerkas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String ktp_file;
    private String kskun_file;
    private String ijazah_file;
    private String akte_file;

    @Enumerated(EnumType.STRING)
    private Status ktpStatus;

    @Enumerated(EnumType.STRING)
    private Status kskunStatus;

    @Enumerated(EnumType.STRING)
    private Status ijazahStatus;

    @Enumerated(EnumType.STRING)
    private Status akteStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_update;

    public enum Status { NONE, UPLOADED, VERIFIED, REJECTED }

    // All-args constructor
    public DtMhsBerkas(Integer id, Integer idcmhsbaru, String ktp_file, String kskun_file, String ijazah_file,
                       String akte_file, Status ktpStatus, Status kskunStatus, Status ijazahStatus,
                       Status akteStatus, Date last_update) {
        this.id = id;
        this.idcmhsbaru = idcmhsbaru;
        this.ktp_file = ktp_file;
        this.kskun_file = kskun_file;
        this.ijazah_file = ijazah_file;
        this.akte_file = akte_file;
        this.ktpStatus = ktpStatus;
        this.kskunStatus = kskunStatus;
        this.ijazahStatus = ijazahStatus;
        this.akteStatus = akteStatus;
        this.last_update = last_update;
    }
}