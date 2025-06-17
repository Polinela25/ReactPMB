package com.pmb.pmb.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


@Entity
@Table(name = "cmhsbaru")
@Getter
@Setter
@NoArgsConstructor
public class CmhsBaru {

    @Id
    @Column(name = "idcmhsbaru")
    private int idcmhsbaru;

    private String namaasli;
    private String username;
    private String email;
    private String password;
    private String activation;
    private String publish;
    private int idrumpun;
    private String noid;

    @Temporal(TemporalType.DATE)
    private Date tgllahir;

    private String tuser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tgllastlogin;

    private java.sql.Time jamlastlogin;

    private String supdate;
    private int idsistempmb;
    private String step0;
    private String step1;
    private String step2;
    private String step3;
    private String step4;
    private String step5;
    private String step6;

    @Temporal(TemporalType.DATE)
    private Date tglbayar;

    private int idjenisbayar;
    private int jumlahbayar;
    private String norekbank;
    private String nmbank;
    private String nmnasabah;
    private String bukti;
    private String trx;
    private String mpin;

    private Integer idukt;
    private int nilai;
    private String kdprodi;
    private String noreg;
    private String keyreg;
    private String paskey;
    private String nohpne;
    private String userip;

    @Temporal(TemporalType.DATE)
    private Date tglvalreg;

    private int idrekening;
    private Integer idjalurpmb;
    private String tahun;
    private String beasiswa;
    private String statusbayar;
    private int spi;
    private String bukti_spi;
    private int jumlahbayar_spi;
    private String va;
    private String vaexpdate;
}
