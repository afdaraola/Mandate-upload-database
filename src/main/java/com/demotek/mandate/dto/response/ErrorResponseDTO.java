package com.demotek.mandate.dto.response;

import com.demotek.mandate.constant.ResponseCodes;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponseDTO {

    private String message;
    private String responseCode;

    public ErrorResponseDTO(ResponseCodes responseCode, String errorMessage){
        this.responseCode = responseCode.getCode();
        this.message=errorMessage;
    }
}
