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
    @Column(name = "id_payment")
    private Integer id;

    @Column(name = "idcmhsbaru")
    private Integer idcmhsbaru;

    @Column(name = "trx_id")
    private String trxId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public enum Status { PENDING, SUCCESS, FAILED }
}
