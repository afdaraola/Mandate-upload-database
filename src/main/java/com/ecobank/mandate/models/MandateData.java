package com.ecobank.mandate.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MandateData {
    private String customerName;
    private String customerNo;
    private String uniqueId;
    private String mandateUpload;
    private String affiliateCode;
    private String mandateCount;
}
