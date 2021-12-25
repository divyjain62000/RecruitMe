package com.recruitme.web.rest.domain.student.registration.drive;

import com.recruitme.enums.error.GlobalError;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.domain.student.registration.drive.StudentRegisteredInDriveService;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;
import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveSearchDTO;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.List;


@RestController
@RequestMapping("/api/student-registered-in-drive")

public class StudentRegisteredInDriveResource {

    @Autowired
    private StudentRegisteredInDriveService studentRegisteredInDriveService;

    /**
     * To add Student
     * @param studentRegisteredInDriveDTO
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error}
     */
    @PostMapping("/add")
    public ResponseEntity<ActionResponse> registerStudentInDrive(@RequestBody StudentRegisteredInDriveDTO studentRegisteredInDriveDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.studentRegisteredInDriveService.save(studentRegisteredInDriveDTO);
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
    public ResponseEntity<?> getAllStudentRegisteredInDrive(@RequestBody StudentRegisteredInDriveSearchDTO studentRegisteredinDriveSearchDTO, Pageable pageable) {
        ActionResponse response=new ActionResponse();
        try {
            Page<StudentRegisteredInDriveResponseDTO> studentRegisteredInDriveResponseDTOS=this.studentRegisteredInDriveService.search(studentRegisteredinDriveSearchDTO,pageable);
            return ResponseEntity.ok().body(studentRegisteredInDriveResponseDTOS);
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

    @ResponseBody
    @GetMapping("/export")
    public void exportStudentRegisterdInDriveExcelFile(@RequestParam("ug") Boolean ug,@RequestParam("pg") Boolean pg,
                                                       @RequestParam("dId") Integer driveId,@RequestParam("bId") Integer branchId, HttpServletResponse httpServletResponse) {
        ActionResponse response=new ActionResponse();
        try {
           StudentRegisteredInDriveSearchDTO studentRegisteredinDriveSearchDTO=new StudentRegisteredInDriveSearchDTO();
            studentRegisteredinDriveSearchDTO.setDriveId(driveId);
            studentRegisteredinDriveSearchDTO.setBranchId(branchId);
            studentRegisteredinDriveSearchDTO.setSearchForUgStudents(ug);
            studentRegisteredinDriveSearchDTO.setSearchForPgStudents(pg);
            ByteArrayInputStream file=this.studentRegisteredInDriveService.exportStudentRegisterdInDriveExcelFile(studentRegisteredinDriveSearchDTO);
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=contacts.xlsx");
            IOUtils.copy(file, httpServletResponse.getOutputStream());
          //  return ResponseEntity.ok().body(file);
        } catch (RecruitmeException recruitmeException) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(recruitmeException.getExceptions());
           // return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
          //  return ResponseEntity.internalServerError().body(response);
        }
    }

}
