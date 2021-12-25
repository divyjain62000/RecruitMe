package com.recruitme.service.auth;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.auth.dto.UserCredentialDTO;
import com.recruitme.service.auth.dto.UserDTO;

public interface AuthService {
    UserDTO authenticateUser(UserCredentialDTO userCredentialDTO);
    boolean resetPassword(String toke,String password) throws RecruitmeException;
    boolean verifyEmail(String token) throws RecruitmeException;
    void requestToRestPassaword(UserCredentialDTO userCredentialDTO) throws RecruitmeException;
}
