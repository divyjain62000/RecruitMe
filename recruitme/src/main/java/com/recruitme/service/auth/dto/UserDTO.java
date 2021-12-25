package com.recruitme.service.auth.dto;

import com.recruitme.enums.Role;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class UserDTO extends DataTransferObject {
    private String role;
    private String email;
    private String enrollmentNumber;
    private Long id;
    private String name;
}
