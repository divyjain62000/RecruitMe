package com.recruitme.web.rest.domain.drive;

import com.recruitme.domain.Drive;
import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.drive.DriveService;
import com.recruitme.service.domain.drive.dto.DriveDTO;
import com.recruitme.service.domain.drive.dto.DriveResponseDTO;
import com.recruitme.service.domain.drive.dto.DriveSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing {@link Drive}
 */
@RestController
@RequestMapping("/api/drive")
public class DriveResource {
    @Autowired
    private DriveService driveService;

    /**
     * To add drive
     * @param driveDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add-drive")
    public ResponseEntity<ActionResponse> addDrive(@RequestBody DriveDTO driveDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.driveService.save(driveDTO);
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
     * To edit drive
     * @param driveDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/edit-drive")
    public ResponseEntity<ActionResponse> editDrive(@RequestBody DriveDTO driveDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.driveService.edit(driveDTO);
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
     * To get all drive or to filter drive
     * @param driveSearchDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @PostMapping("/search")
    public ResponseEntity<Page<DriveDTO>> searchDrives(@RequestBody DriveSearchDTO driveSearchDTO, Pageable pageable) {
        Page<DriveDTO> driveDTOList=this.driveService.search(driveSearchDTO,pageable);
        return ResponseEntity.ok().body(driveDTOList);
    }

    @GetMapping("/")
    public ResponseEntity<List<DriveResponseDTO>> getAllDrive() {
        List<DriveResponseDTO> driveDTOList=this.driveService.findAll();
        return ResponseEntity.ok().body(driveDTOList);
    }


}
