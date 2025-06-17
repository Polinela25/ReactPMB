package com.pmb.pmb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String message;
    private String timestamp;

    public ErrorResponseDTO(int status, String error, String message, String timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
}