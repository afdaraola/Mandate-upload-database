package com.ecobank.mandate.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateMandateRequest {
    private String respMsg;
    private String respCode;
    private String refNum;
}
