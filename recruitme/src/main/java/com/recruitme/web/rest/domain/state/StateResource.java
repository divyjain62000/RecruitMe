package com.recruitme.web.rest.domain.state;

import com.recruitme.domain.State;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.state.StateService;
import com.recruitme.service.domain.state.dto.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing {@link State}
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    @Autowired
    StateService stateService;

    /**
     * get all the state with particular country
     * @param countryId
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @GetMapping("/states/{countryId}")
    public ResponseEntity<?> getAllStateByCountryId(@PathVariable(name="countryId") Integer countryId, Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<StateDTO> states = this.stateService.findAllByCountryId(countryId,pageable);
            return ResponseEntity.ok().body(states);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }

}
