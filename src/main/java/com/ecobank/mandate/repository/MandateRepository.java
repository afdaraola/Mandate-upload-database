package com.ecobank.mandate.repository;

import com.ecobank.mandate.dao.ResponseDao;
import com.ecobank.mandate.models.MandateData;
import com.ecobank.mandate.models.UpdateMandateRequest;
import com.ecobank.mandate.models.UpdateMandateResponse;

import java.util.List;

public interface MandateRepository {

    public ResponseDao<List<MandateData>> fetchMandate();
    public ResponseDao<UpdateMandateResponse> setUpdateMandate(UpdateMandateRequest updateMandateRequest);
}
