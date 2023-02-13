package com.demotek.utils;

/* @author sa_oladipo created on 1/24/22 */


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class HttpUtils {


    /**
     * Constructs and return multi-value map for form data
     * @param requestObject: the request object
     * @return the multi-value map object
     */
    public static MultiValueMap<String, String> getFormData(Map<String, Object> requestObject){

        if(requestObject ==  null)
            return null;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        requestObject.forEach((key, val)-> formData.add(key, String.valueOf(val)));
        return formData;
    }
}
