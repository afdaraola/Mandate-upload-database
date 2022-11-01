package com.ecobank.mandate.services.implementation;

import com.ecobank.mandate.constant.ResponseCodes;
import com.ecobank.mandate.dao.ResponseDao;
import com.ecobank.mandate.dto.response.ResponseDTO;
import com.ecobank.mandate.models.*;
import com.ecobank.mandate.repository.implementation.MandateRepositoryImplementation;
import com.ecobank.mandate.services.MandateService;
import com.ecobank.utils.InterbankBankingProperties;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MandateServiceImplementation implements MandateService {

    @Value("${create-mandate-url}")
    private String createMandateUrl;
    private  final Logger logger =   Logger.getLogger(MandateServiceImplementation.class);

    @Autowired
    MandateRepositoryImplementation mandateRepositoryImplementation;
    private  final RestCallServiceImpl restCallService;

    public  void processRequest()  {
       logger.info("Processing Create Mandate Request");



        ResponseDTO<Object> responseDTO = new ResponseDTO<>();


        String sourceCode = "ECOMOBILE";

      //  String sourceCode = InterbankBankingProperties.getMessage("ENGSOURCECODE");

        logger.info("Source code "+ sourceCode);

        ResponseDao<List<MandateData>> mandateDaoData = mandateRepositoryImplementation.fetchMandate();

        logger.info("after Repo:  "+ mandateDaoData);

        List<MandateData> mandateDataList = mandateDaoData.getPayload();

        responseDTO.setResponseCode(mandateDaoData.getResponseCode());
        responseDTO.setMessage(mandateDaoData.getMessage());

        if (!mandateDaoData.getResponseCode().equals("00")){
            responseDTO.setPayload(mandateDataList);
            return ;
        }

        logger.info("Response data from fetch mandate: "+ mandateDataList);

        logger.info("Cursor  size: "+ mandateDataList.size());

        mandateDataList.stream().forEach(
                mandateData -> {
                    CreateMandateRequest createMandateReq = new CreateMandateRequest()
                            .setAffCode(mandateData.getAffiliateCode())
                            .setCifId(mandateData.getCustomerNo())
                            .setSigId(mandateData.getCustomerNo())
                            .setMsgId(mandateData.getUniqueId() + mandateData.getMandateCount())
                            .setCorrelId(mandateData.getUniqueId() + mandateData.getMandateCount())
                            .setSource(sourceCode)
                            .setUserId(sourceCode)
                            .setSigName(mandateData.getCustomerName())
                            .setFileType(mandateData.getCustomerNo()+"_A_1.jpeg")
                            .setImageText(mandateData.getMandateUpload());

                    logger.info(" Create Mandate Request: "+ createMandateReq);

                    CreateMandateResponse cmResponse  =  restCallService.getResponseConcreteEntity(restCallService.post(createMandateUrl,
                            null, createMandateReq, CreateMandateRequest.class, false), CreateMandateResponse.class, "Couldn't get valid response");

                    logger.info("Create Mandate Response: "+ cmResponse);

                    if(cmResponse!=null){
                        responseDTO.setResponseCode(cmResponse.getResponseCode());
                        responseDTO.setMessage(cmResponse.getResponseMessage());
                    }else {
                        assert cmResponse != null;
                        if (!cmResponse.getResponseCode().equals("000")){
                            //return responseDTO;

                        }else{
                            responseDTO.setResponseCode(ResponseCodes.PROCESS_ERROR.getCode());
                            responseDTO.setMessage(ResponseCodes.PROCESS_ERROR.getMessage());
                            //responseDTO.setPayload(cmResponse);
                            //return responseDTO;
                        }
                    }


                    UpdateMandateRequest updateMandateReq = new UpdateMandateRequest()
                            .setRespMsg(cmResponse.getResponseMessage())
                            .setRespCode(cmResponse.getResponseCode())
                            .setRefNum(mandateData.getUniqueId());

                    logger.info(" Update Mandate Request: "+ updateMandateReq);

                    ResponseDao<UpdateMandateResponse> updateMandateDaoData = mandateRepositoryImplementation.setUpdateMandate(updateMandateReq);

                    UpdateMandateResponse updateMandateResp = updateMandateDaoData.getPayload();

                    logger.info("Update Mandate Response: "+
                            "UniqueId: "+ mandateData.getUniqueId()+
                            " Data: "+ updateMandateResp);
                }

        );
    }


}
