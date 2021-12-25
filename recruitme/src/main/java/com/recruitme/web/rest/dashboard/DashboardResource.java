package com.recruitme.web.rest.dashboard;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.dashboard.DashboardService;
import com.recruitme.service.domain.address.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class DashboardResource {


    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard-data")
    public ResponseEntity<ActionResponse> getDashBoardData() {
        ActionResponse actionResponse=new ActionResponse();
        try {
            List<Integer> data=this.dashboardService.getData();
            actionResponse.setSuccessful(true);
            actionResponse.setResult(data);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }
}
