package com.ecobank.mandate.dao;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDao<T> {
    private String message;
    private String responseCode;
    private T payload;
}
