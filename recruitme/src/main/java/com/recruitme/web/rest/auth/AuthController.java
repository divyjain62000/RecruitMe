package com.recruitme.web.rest.auth;

import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) throws RecruitmeException {
        log.debug("token: {}",token);
        boolean success=this.authService.verifyEmail(token);
        return success?"VerificationSuccessful":"Failure";
    }

    @GetMapping("/error")
    public String error(@RequestParam("token") String token) throws RecruitmeException {
        return "VerificationSuccessful";
    }
}
