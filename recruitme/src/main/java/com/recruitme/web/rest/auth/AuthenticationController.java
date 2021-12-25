package com.recruitme.web.rest.auth;


import com.recruitme.enums.Role;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.response.ActionResponse;
import com.recruitme.service.auth.AuthService;
import com.recruitme.service.auth.dto.UserCredentialDTO;
import com.recruitme.service.auth.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public ActionResponse authenticateUser(@RequestBody UserCredentialDTO userCredentialDTO, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        HttpSession httpSession = request.getSession();
        if (httpSession == null) {
            log.debug("Session not active");
            httpSession = request.getSession(true);
        }
        UserDTO userDTO = this.authService.authenticateUser(userCredentialDTO);
        if (userDTO.getEmail() == null && userDTO.getRole() == null) {
            ActionResponse response = new ActionResponse();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult("Invalid Credentials");
            return response;
        } else {
            httpSession.setAttribute("user", userDTO);
            ActionResponse response = new ActionResponse();
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(userDTO);
            return response;
        }
    }

    @RequestMapping(value="/reset-password",method = RequestMethod.POST)
    public ActionResponse resetPassword(@RequestParam("token") String token,@RequestParam("password") String password) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            this.authService.resetPassword(token,password);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return actionResponse;
        }catch(RecruitmeException recruitmeException) {
            log.debug("exception recruitme");
            actionResponse.setSuccessful(false);
            actionResponse.setResult(recruitmeException.getExceptions());
            actionResponse.setException(true);
            return actionResponse;
        }catch (Exception exception) {
            log.debug("exception");
            actionResponse.setSuccessful(false);
            actionResponse.setResult(exception.getStackTrace());
            exception.printStackTrace();
            actionResponse.setException(true);
            return actionResponse;
        }
    }


    @PostMapping("/request-reset-password")
    public ActionResponse requestToResetPassword(@RequestBody UserCredentialDTO userCredentialDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try {
            this.authService.requestToRestPassaword(userCredentialDTO);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return actionResponse;
        }catch(RecruitmeException recruitmeException) {
            actionResponse.setSuccessful(false);
            actionResponse.setResult(recruitmeException.getExceptions());
            actionResponse.setException(true);
            return actionResponse;
        }catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setResult(exception.getStackTrace());
            actionResponse.setException(true);
            return actionResponse;
        }
    }

}
