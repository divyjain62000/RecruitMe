package com.recruitme.service.google.drive.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.recruitme.service.google.drive.GoogleDriveService;
import com.recruitme.service.google.drive.auth.GoogleDriveAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class GoogleDriveServiceImpl implements GoogleDriveService {

    private static final String APPLICATION_NAME = "recruitme-dev";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    @Override
    public File upload(MultipartFile uploadFile) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleDriveAuth.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File fileMetadata = new File();
        /*File file = driveService.files().create(fileMetadata, new InputStreamContent(
                        uploadFile.getContentType(),
                        new ByteArrayInputStream(uploadFile.getBytes())))
                .setFields("id")
                .execute();
        file.*/
        fileMetadata.setName(LocalDateTime.now()+"");
        fileMetadata.setOriginalFilename(LocalDateTime.now()+"");
        Drive.Files.Create create=driveService.files().create(fileMetadata,new InputStreamContent(uploadFile.getContentType(),
                new ByteArrayInputStream(uploadFile.getBytes())));
        MediaHttpUploader uploader=create.getMediaHttpUploader();
        AtomicReference<Boolean> fileUploaded= new AtomicReference<>(false);
        uploader.setProgressListener((uploading)->
        {
            switch (uploading.getUploadState())
            {
                case INITIATION_STARTED:System.out.println("Initiation has started!");
                    break;
                case INITIATION_COMPLETE:System.out.println("Initiation is complete!");
                    break;
                case MEDIA_IN_PROGRESS:
                    //System.out.println("Progress="+uploading.getProgress())
                    fileUploaded.set(false);
                    System.out.println("Bytes="+uploading.getNumBytesUploaded());
                    System.out.println("Upload State="+uploading.getUploadState().name());
                    break;
                case MEDIA_COMPLETE:
                    System.out.println("Upload is complete!");
                    fileUploaded.set(true);
            }
        });
        File file=create.execute();
        System.out.println("File ID: " + file.getId());
        System.out.println("File Name: " + file.getName());
        System.out.println("File Name: " + file.getOriginalFilename());
        Permission newPermission=new Permission();
        newPermission.setAllowFileDiscovery(true);
        newPermission.setType("anyone");
        newPermission.setRole("reader");
        Drive.Permissions.Create createFilePermission=driveService.permissions().create(file.getId(), newPermission);
        Permission updatedPermission=createFilePermission.execute();
        log.debug("Permission Updated!");
        return file;
    }

}
