package com.recruitme.model;

import com.recruitme.domain.StudentHoldingOffers;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOffersDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StudentHoldingOffersModel {
    public static Map<Long,List<StudentHoldingOffersDTO>> studentHoldingOffersDTOMap;

    private StudentHoldingOffersModel(){}

    static
    {
        studentHoldingOffersDTOMap=new HashMap<>();
    }
}
