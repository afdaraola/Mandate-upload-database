package com.demotek.mandate.dto.response;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDTO<T> {

    private String message;
    private String responseCode;
    private T payload;


}
