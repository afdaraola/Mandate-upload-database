package com.ecobank.mandate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateMandateResponse {
    private  String responseCode;
    private  String responseMessage;
}
