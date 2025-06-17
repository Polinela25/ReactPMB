package com.pmb.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "dt_mhs_payment")
@Getter
@Setter
@NoArgsConstructor
public class DtMhsPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "trx_id")
    private String trxId;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public enum Status { PENDING, SUCCESS, FAILED, CANCELLED }

    // All-args constructor
    public DtMhsPayment(Integer id, String trxId, Integer idcmhsbaru, Double amount, Status status, Date lastUpdate) {
        this.id = id;
        this.trxId = trxId;
        this.idcmhsbaru = idcmhsbaru;
        this.amount = amount;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}