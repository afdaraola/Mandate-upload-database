package com.ecobank.mandate.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MandateRequest {
    private String affCode;
    private String source;
}
