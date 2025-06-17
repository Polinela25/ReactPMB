package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtMhsPaymentDTO {
    private Integer id;
    private String trxId;
    private Double amount;
    private String status;
    private String lastUpdate;

    public DtMhsPaymentDTO(Integer id, String trxId, Double amount, String status, String lastUpdate) {
        this.id = id;
        this.trxId = trxId;
        this.amount = amount;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
}