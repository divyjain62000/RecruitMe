package com.recruitme.web.rest.domain.placement.coordinator;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.placement.coordinator.PlacementCoordinatorService;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorResponseDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorSearchDTO;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.domain.student.dto.StudentSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Rest Controller for managing {@link PlacementCoordinator}
 */
@RestController
@RequestMapping("/api/placement-coordinator")
public class PlacementCoordinatorResource {

    @Autowired
    private PlacementCoordinatorService placementCoordinatorService;

    /**
     *  To add Placement Coordinator
     * @param placementCoordinatorDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-placement-coordinator")
    public ResponseEntity<ActionResponse> addPlacementCoordinator(@RequestBody PlacementCoordinatorDTO placementCoordinatorDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.placementCoordinatorService.save(placementCoordinatorDTO);
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
     * To edit Placement Coordinator
     * @param placementCoordinatorDTO
     * @return
     */
    @PostMapping("/edit-placement-coordinator")
    public ResponseEntity<ActionResponse> editStudent(@RequestBody PlacementCoordinatorDTO placementCoordinatorDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.placementCoordinatorService.edit(placementCoordinatorDTO);
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
     * To get all placement coordinator or to filter placement coordinator
     * @param placementCoordinatorSearchDTO
     * @param pageable
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @PostMapping("/search")
    public ResponseEntity<Page<PlacementCoordinatorResponseDTO>> searchPlacementCoordinators(@RequestBody PlacementCoordinatorSearchDTO placementCoordinatorSearchDTO, Pageable pageable) {
        Page<PlacementCoordinatorResponseDTO> placementCoordinatorResponseDTOList=this.placementCoordinatorService.search(placementCoordinatorSearchDTO,pageable);
        return ResponseEntity.ok().body(placementCoordinatorResponseDTOList);
    }

}
