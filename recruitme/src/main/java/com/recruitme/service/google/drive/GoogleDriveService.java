package com.recruitme.service.google.drive;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleDriveService {
    File upload(MultipartFile uploadFile) throws IOException, GeneralSecurityException;
}
