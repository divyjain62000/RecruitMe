package com.recruitme.service.auth.dto;

import com.recruitme.enums.Role;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class UserCredentialDTO extends DataTransferObject {
    private String emailOrUsername;
    private String password;
    private Role role;

}
