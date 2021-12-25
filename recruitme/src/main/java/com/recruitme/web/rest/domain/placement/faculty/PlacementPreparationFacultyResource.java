package com.recruitme.web.rest.domain.placement.faculty;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.placement.faculty.PlacementPreparationFacultyService;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyResponseDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultySearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest Controller for managing {@link PlacementPreparationFacultyResource}
 */
@RestController
@RequestMapping("/api/placement-preparation-faculty")
public class PlacementPreparationFacultyResource {

    @Autowired
    private PlacementPreparationFacultyService placementPreparationFacultyService;

    /**
     * To add Placement Preparation Faculty
     * @param placementPreparationFacultyDTO
     * @return
     */
    @PostMapping("/add-placement-preparation-faculty")
    public ResponseEntity<ActionResponse> addStudent(@RequestBody PlacementPreparationFacultyDTO placementPreparationFacultyDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.placementPreparationFacultyService.save(placementPreparationFacultyDTO);
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
     * To edit Placement Preparation Faculty
     * @param placementPreparationFacultyDTO
     * @return
     */
    @PostMapping("/edit-placement-preparation-faculty")
    public ResponseEntity<ActionResponse> editStudent(@RequestBody PlacementPreparationFacultyDTO placementPreparationFacultyDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.placementPreparationFacultyService.edit(placementPreparationFacultyDTO);
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


    @PostMapping("/search")
    public ResponseEntity<Page<PlacementPreparationFacultyResponseDTO>> searchPlacementCoordinators(@RequestBody PlacementPreparationFacultySearchDTO placementPreparationFacultySearchDTO, Pageable pageable) {
        Page<PlacementPreparationFacultyResponseDTO> placementPreparationFacultyResponseDTOList=this.placementPreparationFacultyService.search(placementPreparationFacultySearchDTO,pageable);
        return ResponseEntity.ok().body(placementPreparationFacultyResponseDTOList);
    }


}
