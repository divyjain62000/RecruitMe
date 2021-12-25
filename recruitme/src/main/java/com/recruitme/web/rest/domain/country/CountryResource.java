package com.recruitme.web.rest.domain.country;

import com.recruitme.domain.Country;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.country.CountryService;
import com.recruitme.service.domain.country.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link Country}
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    @Autowired
    private CountryService countryService;

    /**
     * {@code GET /countries} : get all the Country
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @GetMapping("/countries")
    public ResponseEntity<?> getAllCountry(Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<CountryDTO> countries = this.countryService.findAll(pageable);
            return ResponseEntity.ok().body(countries);
        } catch (Exception exception) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }

}
