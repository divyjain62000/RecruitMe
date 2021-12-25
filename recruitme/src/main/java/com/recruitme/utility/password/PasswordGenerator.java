package com.recruitme.utility.password;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasswordGenerator {

    public static String generatePassword() {
        int length = 6;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        log.debug("Generated password: {}",generatedPassword);
        return generatedPassword;
    }

}
