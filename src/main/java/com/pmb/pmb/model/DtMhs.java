package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dt_mhs")
@Getter
@Setter
@NoArgsConstructor
public class DtMhs {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String nopendaftaran;
    private String nama;
    private String sex;
    private String tmplahir;
    private String tgllahir;
    private String agama;
    private String statusnikah;
    private String anakke;
    private String jmlsaudara;

    @Enumerated(EnumType.STRING)
    private BidikmisiStatus bidikmisi;

    public enum BidikmisiStatus { ZERO, ONE, TWO }

}