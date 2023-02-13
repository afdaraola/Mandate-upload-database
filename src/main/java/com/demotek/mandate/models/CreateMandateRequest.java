package com.demotek.mandate.models;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateMandateRequest {
    private String affCode;
    private String source;
    private String msgId;
    private String correlId;
    private String userId;
    private String cifId;
    private String sigId;
    private String sigName;
    private String fileType;
    private String imageText;

}
