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
    @Column(name = "idpilihan")
    private Integer idpilihan;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private String idprodiy;
    private Integer idprodi;
    private Integer pilihanke;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer idbiaya;

    @Enumerated(EnumType.STRING)
    private PublishStatus publish;

    private Integer spipendaftar;

    @Enumerated(EnumType.STRING)
    private KelasStatus kelas;

    public enum Status { ZERO, ONE, TWO }
    public enum PublishStatus { T, F }
    public enum KelasStatus { R, I }
}