package com.recruitme.web.rest.domain.city;

import com.recruitme.domain.City;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.city.CityService;
import com.recruitme.service.domain.city.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing {@link City}
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    @Autowired
    CityService cityService;

    /**
     * get all the city with particular state
     * @param stateId
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @GetMapping("/cities/{stateId}")
    public ResponseEntity<?> getAllCityByStateId(@PathVariable(name="stateId") Integer stateId, Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<CityDTO> cities = this.cityService.findAllByStateId(stateId,pageable);
            return ResponseEntity.ok().body(cities);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }

}
