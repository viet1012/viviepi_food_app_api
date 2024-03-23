package com.food_app_api.Viviepi.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.food_app_api.Viviepi.config.CloudinaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Component
public class CloudinaryUtil {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String, Object> upload(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

    public String uploadImageToFolder(MultipartFile file, String folderName) throws IOException {
        try {
            // Tạo một thư mục mới trên Cloudinary
            createFolder(folderName);

            // Tải lên hình ảnh vào thư mục mới được tạo
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folderName
            ));

            // Trả về đường dẫn của hình ảnh sau khi đã được tải lên
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new IOException("Failed to upload image to Cloudinary", e);
        }
    }

    private void createFolder(String folderName) {
        try {
            // Tạo một map chứa thông tin cho việc tạo thư mục
            Map params = ObjectUtils.asMap(
                    "type", "upload",
                    "prefix", folderName + "/"
            );

            // Gửi yêu cầu tạo thư mục lên Cloudinary
            cloudinary.api().createFolder(folderName, params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create folder on Cloudinary", e);
        }
    }

}
