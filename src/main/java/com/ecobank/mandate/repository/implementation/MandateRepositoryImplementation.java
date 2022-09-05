package com.ecobank.mandate.repository.implementation;

import com.ecobank.mandate.dao.ResponseDao;
import com.ecobank.mandate.exception.GenericException;
import com.ecobank.mandate.constant.ResponseCodes;
import com.ecobank.mandate.models.MandateData;
import com.ecobank.mandate.models.UpdateMandateRequest;
import com.ecobank.mandate.models.UpdateMandateResponse;
import com.ecobank.mandate.repository.MandateRepository;
import com.ecobank.utils.RepositoryUtils;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import oracle.jdbc.OracleCallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MandateRepositoryImplementation implements MandateRepository {

    @Value("${mandate-package-name}")
    String packageName;

    private static final Logger logger = LoggerFactory.getLogger(MandateRepositoryImplementation.class);

    public ResponseDao<List<MandateData>> fetchMandate(){
        ResponseDao<List<MandateData>> responseDao = new ResponseDao<>();

        Connection conn = null;
        OracleCallableStatement cSt = null;
        ResultSet rs = null;

        try{
          // conn = RepositoryUtils.getConnection();
            System.out.println("===before Opening Connection 1");
           // conn = RepositoryUtils.getDataBaseConnectionFlex();
            conn = RepositoryUtils.getConnection();
            cSt = (OracleCallableStatement)  conn.prepareCall("{call "+packageName+".pr_fetch_mandate(?, ?, ?)}");
            cSt.registerOutParameter(1, OracleTypes.VARCHAR);
            cSt.registerOutParameter(2, OracleTypes.VARCHAR);
            cSt.registerOutParameter(3, OracleTypes.CURSOR);

            cSt.execute();

            String code = cSt.getString(1);
            String message = cSt.getString(2);
            rs = (ResultSet) cSt.getObject(3);

            logger.info("Code: "+ code);
            logger.info("Message: "+ message);

            List<MandateData> mandate = getMandateFromResultSet(rs);


            logger.info("Mandate: "+ mandate);

            responseDao.setPayload(mandate).setResponseCode(code).setMessage(message);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            responseDao.setResponseCode(ResponseCodes.PROCESS_ERROR.getCode()).setMessage(ResponseCodes.PROCESS_ERROR.getMessage());
            throw new GenericException(ResponseCodes.PROCESS_ERROR, e.getMessage(), null);
        }finally {
            RepositoryUtils.closeFinally(conn, cSt, rs);

        }
        return responseDao;
    }
    @Override
    public ResponseDao<UpdateMandateResponse> setUpdateMandate(UpdateMandateRequest updateMandateRequest){
        ResponseDao<UpdateMandateResponse> responseDao = new ResponseDao<>();
        String message = "Failed!";
        String code = "99" ;
        Connection conn = null;
        OracleCallableStatement cSt = null;
        UpdateMandateResponse updateMandateResponse = new UpdateMandateResponse(message, code);
        try{
          //  conn = RepositoryUtils.getConnection();
            System.out.println("===before Opening Connection 2");
           // conn = RepositoryUtils.getDataBaseConnectionFlex();
            conn = RepositoryUtils.getConnection();

            cSt = (OracleCallableStatement) conn.prepareCall("{call "+packageName+".pr_update_mandate(?, ?, ?, ?, ?)}");

            cSt.setString(1, updateMandateRequest.getRespCode());
            cSt.setString(2, updateMandateRequest.getRespMsg());
            cSt.setString(3, updateMandateRequest.getRefNum());
            cSt.registerOutParameter(4, OracleTypes.VARCHAR);
            cSt.registerOutParameter(5, OracleTypes.VARCHAR);
            cSt.execute();

            code = cSt.getString(4);
            message = cSt.getString(5);

            updateMandateResponse.setResponseCode(code).setResponseMessage(message);

            logger.info("Response: "+ updateMandateResponse);

            responseDao.setPayload(updateMandateResponse).setResponseCode(code).setMessage(message);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            responseDao.setResponseCode(ResponseCodes.PROCESS_ERROR.getCode()).setMessage(ResponseCodes.PROCESS_ERROR.getMessage());

            throw new GenericException(ResponseCodes.PROCESS_ERROR, e.getMessage(), null);
        }finally {
            RepositoryUtils.closeFinally(conn, cSt, null);
        }

        return responseDao;
    }

    public List<MandateData> getMandateFromResultSet(ResultSet rs) throws Exception {
        List<MandateData> mandateDataList = null;
        try{
            if(rs == null)
                return mandateDataList;

            System.out.println("****fetch size***  "+rs.getFetchSize());
            mandateDataList = new ArrayList<>();
            while(rs.next()) {

//                System.out.println("customer_name1  "+ rs.getString("customer_name1"));
//                System.out.println("customer_no  "+ rs.getString("customer_no"));
//                System.out.println("uniq_id "+ rs.getString("uniq_id"));
//                System.out.println("mandate_upload  "+ rs.getString("mandate_upload"));
//                System.out.println("affiliate_code "+ rs.getString("affiliate_code"));
                mandateDataList.add(
                        new MandateData().setCustomerName(rs.getString("customer_name1"))
                                .setCustomerNo(rs.getString("customer_no"))
                                .setUniqueId(rs.getString("uniq_id"))
                                .setMandateUpload(rs.getString("mandate_upload"))
                                .setAffiliateCode(rs.getString("affiliate_code"))
                                .setMandateCount(rs.getString("mandate_count"))


                );

            }
        }catch(Exception ex){
            logger.info("EXCPTiOB ::::", ex.getMessage(), ex);
        }

        return mandateDataList;
    }

}





