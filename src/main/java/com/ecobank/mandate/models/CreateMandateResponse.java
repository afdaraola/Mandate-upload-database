package com.ecobank.mandate.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateMandateResponse {

    private String responseCode;
    private String responseMessage;
    private Data data;

    @lombok.Data
    public static class Data{
        @JsonProperty("FCUBS_WARNING_RESP")
        private String FCUBS_WARNING_RESP;
    }


}
