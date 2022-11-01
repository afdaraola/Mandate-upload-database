package com.ecobank.mandate.services.implementation;


import com.ecobank.mandate.constant.ResponseCodes;
import com.ecobank.mandate.controller.MandateController;
import com.ecobank.mandate.dto.response.ResponseDTO;

import org.apache.log4j.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CreateMandateJob {
    private final Logger logger = Logger.getLogger(MandateController.class);
    private final MandateServiceImplementation mandateService;

    public CreateMandateJob(MandateServiceImplementation mandateService) {
        this.mandateService = mandateService;
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 8000)
    public void getCreateJob(){

        System.out.println("====== Entry Point=========");

        ResponseDTO resp = new ResponseDTO();
        try {
            mandateService.processRequest();
        } catch (Exception e) {
            logger.error(e.toString());
            resp.setResponseCode(ResponseCodes.PROCESS_ERROR.getCode());
            resp.setMessage(ResponseCodes.PROCESS_ERROR.getMessage());
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resp);
        }
    }
}
