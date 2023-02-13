package com.demotek.mandate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodes {

    SUCCESS("000", "Successful"),
    ALREADY_EXIST("22", "Already exists"),
    NOT_FOUND("11", "Not found"),
    VALIDATION_ERROR("33", "Validation error occurred"),
    PROCESS_ERROR("99", "Error occurred during processing"),
    INVALID_CREDENTIAL("44", "Invalid credentials"),
    BAD_DATA("77", "Invalid data provided");

    private String code;
    private String message;
}
