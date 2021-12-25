package com.recruitme.service.dashboard.impl;

import com.recruitme.model.StudentHoldingOffersModel;
import com.recruitme.model.StudentModel;
import com.recruitme.service.dashboard.DashboardService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Override
    public List<Integer> getData() {
        List<Integer> data=new LinkedList<>();
        data.add(StudentModel.studentList.size());
        data.add(StudentHoldingOffersModel.studentHoldingOffersDTOMap.size());
        return data;
    }
}
