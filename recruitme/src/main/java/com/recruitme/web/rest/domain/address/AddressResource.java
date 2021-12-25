package com.recruitme.web.rest.domain.address;

import com.recruitme.domain.Country;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.address.AddressService;
import com.recruitme.service.domain.address.dto.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link Country}
 */
@RestController
@RequestMapping("/api")
public class AddressResource {


    @Autowired
    private AddressService addressService;

    /**
     * To add address
     * @param addressDTO
     * @return  the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-address")
    public ResponseEntity<ActionResponse> addAddress(@RequestBody AddressDTO addressDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            Integer addressId=this.addressService.save(addressDTO);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(addressId);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        } catch (RecruitmeException recruitmeException) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

    /**
     * To edit address
     * @param addressDTO
     * @return  the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/edit-address")
    public ResponseEntity<ActionResponse> editAddress(@RequestBody AddressDTO addressDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            Integer addressId=this.addressService.edit(addressDTO);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(addressId);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        } catch (RecruitmeException recruitmeException) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(actionResponse);
        } catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

    @PostMapping("/get-address/{id}")
    public ResponseEntity<ActionResponse> findById(@PathVariable(name="id") Integer id) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            AddressDTO addressDTO=this.addressService.findById(id);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(addressDTO);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setException(true);
            actionResponse.setResult(exception.getStackTrace());
            exception.getStackTrace(); // later on change to log
            return ResponseEntity.ok().body(actionResponse);
        }
    }

}
