package com.food_app_api.Viviepi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IUploadFileService {
    List<File> getAllGoogleDriveFiles();
    String createdNewFolders(String folderName);
    void deleteFiles(String fileId);
}
