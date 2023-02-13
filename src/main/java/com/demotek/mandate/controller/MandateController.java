package com.demotek.mandate.controller;

import com.demotek.mandate.constant.ResponseCodes;
import com.demotek.mandate.dto.response.ResponseDTO;
import com.demotek.mandate.models.CreateMandateResponse;
import com.demotek.mandate.services.implementation.MandateServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mandate")
public class MandateController {

    private static final Logger logger = LoggerFactory.getLogger(MandateController.class);


    private final MandateServiceImplementation mandateService;

    @PostMapping(value = "util")
    public ResponseEntity<ResponseDTO<CreateMandateResponse>> createMandate() {

        ResponseDTO resp = new ResponseDTO();
        try {
            mandateService.processRequest();
            resp.setPayload(null);
            resp.setResponseCode(null);
            resp.setMessage(null);
//            if(cmResp.getResponseCode().equals(ResponseCodes.SUCCESS.getCode())){
//                return ResponseEntity.status(HttpStatus.OK).body(resp);
//            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            logger.error(e.toString());
            resp.setResponseCode(ResponseCodes.PROCESS_ERROR.getCode());
            resp.setMessage(ResponseCodes.PROCESS_ERROR.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resp);
        }
        }

    }

