package com.recruitme.web.rest.domain.student.holding.offer;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.student.holding.offers.StudentHoldingOffersService;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOfferResponseDTO;
import com.recruitme.service.domain.student.holding.offers.dto.StudentHoldingOffersDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveSearchDTO;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;


/**
 * The type Student holding offers resource.
 */
@RestController
@RequestMapping("/api/student-holding-offers")

public class StudentHoldingOffersResource {

    @Autowired
    private StudentHoldingOffersService studentHoldingOffersService;

    /**
     * To add Student
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add")
    public ResponseEntity<ActionResponse> addStudentHoldingOffers(@RequestBody StudentHoldingOffersDTO studentHoldingOffersDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.studentHoldingOffersService.save(studentHoldingOffersDTO);
            response.setSuccessful(true);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (RecruitmeException recruitmeException) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * To edit Student
     *
     * @param studentHoldingOffersDTO the student holding offers dto
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/edit")
    public ResponseEntity<ActionResponse> editStudentHoldingOffers(@RequestBody StudentHoldingOffersDTO studentHoldingOffersDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.studentHoldingOffersService.edit(studentHoldingOffersDTO);
            response.setSuccessful(true);
            response.setException(false);
            return ResponseEntity.ok().body(response);
        } catch (RecruitmeException recruitmeException) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Find all student holding offers.
     *
     * @return the response entity
     */
    @GetMapping("/")
    public ResponseEntity<List<StudentHoldingOfferResponseDTO>> findAll() {
        return ResponseEntity.ok().body(this.studentHoldingOffersService.findAll());
    }


    /**
     * Find by student id response entity.
     *
     * @param studentId the student id
     * @return the response entity
     */
    @GetMapping("/student")
    public ResponseEntity<List<StudentHoldingOffersDTO>> findByStudentId(@RequestParam("studentId") Long studentId) {
        return ResponseEntity.ok().body(this.studentHoldingOffersService.findByStudentId(studentId));
    }
}
