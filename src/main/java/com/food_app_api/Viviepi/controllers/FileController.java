package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.util.UploadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/file/api")
public class FileController {

    @Autowired
    private UploadLocalUtil uploadLocalUtil;

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file,
    @RequestParam("folderName")String folderName) {
        try {
            String fileName = uploadLocalUtil.storeFile(file, folderName);
            ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(),
                    "File uploaded successfully", folderName);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            ResponseObject responseObject = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to upload file", null);
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseObject> listFiles(@RequestParam("folderName") String folderName) {
        try {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(),
                    "Successfully retrieved file list",
                    uploadLocalUtil.loadAll(folderName).map(path ->
                            path.getFileName().toString()).collect(Collectors.toList())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to retrieve file list", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName,
                                               @RequestParam("folderName") String folderName) {
        byte[] content = uploadLocalUtil.readFileContent(fileName, folderName);
        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + fileName)
                .body(content);
    }
}
