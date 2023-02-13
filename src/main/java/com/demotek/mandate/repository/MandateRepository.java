package com.demotek.mandate.repository;

import com.demotek.mandate.dao.ResponseDao;
import com.demotek.mandate.models.MandateData;
import com.demotek.mandate.models.UpdateMandateRequest;
import com.demotek.mandate.models.UpdateMandateResponse;

import java.util.List;

public interface MandateRepository {

    public ResponseDao<List<MandateData>> fetchMandate();
    public ResponseDao<UpdateMandateResponse> setUpdateMandate(UpdateMandateRequest updateMandateRequest);
}
