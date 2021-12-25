package com.recruitme.service.domain.drive;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.drive.dto.DriveDTO;
import com.recruitme.domain.Drive;
import com.recruitme.service.domain.drive.dto.DriveResponseDTO;
import com.recruitme.service.domain.drive.dto.DriveSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface to manage Drive
 */
public interface DriveService {
    /**
     * Declaration of save method, its function is to save {@link Drive}
     * @param driveDTO
     * @throws RecruitmeException
     */
    void save(DriveDTO driveDTO) throws RecruitmeException;

    /**
     * Declaration of edit method, its function is to edit {@link Drive}
     * @param driveDTO
     * @throws RecruitmeException
     */
    void edit(DriveDTO driveDTO) throws RecruitmeException;

    /**
     * To get all drive or to filter drive
     * @param driveSearchDTO
     * @param pageable
     * @return Page of type {@link DriveDTO}
     */
    Page<DriveDTO> search(DriveSearchDTO driveSearchDTO, Pageable pageable);

    /**
     *To get all drives
     * @return {@link List <DriveDTO>}
     */
    List<DriveResponseDTO> findAll();
}
