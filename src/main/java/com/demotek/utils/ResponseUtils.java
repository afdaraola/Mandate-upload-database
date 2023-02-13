package com.demotek.utils;




import com.demotek.mandate.constant.ResponseCodes;
import com.demotek.mandate.dao.ResponseDao;
import com.demotek.mandate.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;


/* @author sa_oladipo created on 1/24/22 */


@Slf4j
public class ResponseUtils {


    /**
     * Returns successful response for controllers with method return holders
     * @param payload: the payload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseEntity<ResponseDTO<T>> getSuccessfulControllerResponse(T payload){

        return ResponseEntity.ok().body(
                new ResponseDTO<T>()
                        .setResponseCode(ResponseCodes.SUCCESS.getCode())
                        .setMessage(ResponseCodes.SUCCESS.getMessage())
                        .setPayload(payload)
        );
    }

    /**
     * Returns successful response for controllers with general response holder
     * @param payload: the general responsepayload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseDao<T> getSuccessfulRepositoryResponse(T payload, String message, String code){

        return new ResponseDao<T>()
                .setResponseCode(code)
                .setMessage(message)
                .setPayload(payload);
    }








}
