package com.food_app_api.Viviepi.services;
import com.food_app_api.Viviepi.dto.GoogleTokenDTO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirebaseUserService {

    public List<String> getUsers() throws Exception {
        // Khởi tạo Firebase Admin SDK
        FirebaseApp app = FirebaseApp.getInstance("viviepi-app");
        FirebaseAuth auth = FirebaseAuth.getInstance(app);

        // Lấy danh sách người dùng từ Firebase Authentication
        ListUsersPage page = auth.listUsers(null);
        List<String> userEmails = new ArrayList<>();

        // Lặp qua danh sách người dùng và lấy email của từng người dùng
        for (UserRecord user : page.getValues()) {
            userEmails.add(user.getEmail());
        }

        return userEmails;
    }

    public ResponseEntity<String> loginWithGoogle(GoogleTokenDTO googleTokenDTO) {
        try {
            // Xác thực token từ Google với Firebase Authentication
            FirebaseApp app = FirebaseApp.getInstance("viviepi-app");

            FirebaseToken decodedToken = FirebaseAuth.getInstance(app).verifyIdToken(googleTokenDTO.getIdToken());

            // Lấy thông tin tài khoản người dùng từ token
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            // Xử lý logic đăng nhập ở đây (lưu thông tin người dùng vào database, tạo phiên làm việc, vv.)

            return ResponseEntity.ok("Login successful " + email +" UID: "+  uid);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
}
