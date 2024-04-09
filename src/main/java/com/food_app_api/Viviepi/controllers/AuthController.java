package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.ResetPasswordDTO;
import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.exceptions.AlreadyExistException;
import com.food_app_api.Viviepi.exceptions.PermissionDenyException;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseError;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseSuccess;
import com.food_app_api.Viviepi.services.AccountService;
import com.food_app_api.Viviepi.util.EmailUtil;
import com.google.firebase.FirebaseApp;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    private FirebaseUserService authService;

//    @Autowired
//    UserSessionService userSessionService;
//    @GetMapping("/info")
//    public ResponseEntity<ResponseObject> getInfoUserBySession(@RequestBody UserSession userSession) {
//        System.out.println("UserID: " + userSession.getUserId());
//        UserSession user = userSessionService.getUserSession(userSession.getUserId());
//        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Info User: ", user);
//
//        return new ResponseEntity<>(responseObject, HttpStatus.OK);
//    }

//    @GetMapping("/firebase/users")
//    public List<String> getUsers() throws Exception {
//        // Khởi tạo Firebase Admin SDK
//        FirebaseApp app = FirebaseApp.getInstance("viviepi-app");
//        FirebaseAuth auth = FirebaseAuth.getInstance(app);
//
//
//        // Lấy danh sách người dùng từ Firebase Authentication
//        ListUsersPage page = auth.listUsers(null);
//        List<String> userEmails = new ArrayList<>();
//
//        // Lặp qua danh sách người dùng và lấy email của từng người dùng
//        for (UserRecord user : page.getValues()) {
//            userEmails.add(user.getEmail());
//        }
//
//        return userEmails;
//    }

    @GetMapping("/get/info")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> getInfoUser(@RequestHeader("Authorization") String authorization) {
        // Lấy token từ header Authorization
        String token = authorization.substring("Bearer ".length());
        System.out.println("token " + token);
        UserDTO userDTO = accountService.getUserInfo(token);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Info User: ", userDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/test-token")
    @Transactional(rollbackFor = Exception.class)
    public String yourMethod(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) {
        // Lấy token từ header Authorization
        String token = authorizationHeader.substring("Bearer ".length());
        UUID uuid = UUID.randomUUID();

        return "Your response: " + token + "UUID: " + uuid ;
    }

    @PostMapping("/sign-in")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> SignInUser(@RequestBody SignInRequest signInDTO) {
        try {
            ResponseObject responseObject = accountService.signInUser(signInDTO);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (PermissionDenyException e) {
            ResponseObject errorResponse = new ResponseObject(e.getStatusCode(),"Email or Password is  not valid ", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/sign-in/admin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> SignInAdmin (@RequestBody SignInRequest signInDTO) throws MessagingException, UnsupportedEncodingException  {

        return new ResponseEntity<>(accountService.signInAdmin(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> SignUpUser(@Valid @RequestBody SignUpRequest signUpRequest) throws AlreadyExistException, UnsupportedEncodingException {
        ResponseSuccess success = new ResponseSuccess();
        if(accountService.checkEmailExists(signUpRequest.getEmail())){
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
        }
        signUpRequest.setRoleName("ROLE_USER");
        success.setStatus(HttpStatus.OK.value());
        success.setData(accountService.signUp(signUpRequest));
        return new ResponseEntity<>(success,HttpStatus.OK);

    }

    @PostMapping("/sign-up/admin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> SignUpAdmin(@Valid @RequestBody SignUpRequest signUpRequest) throws AlreadyExistException, UnsupportedEncodingException {
        ResponseSuccess success = new ResponseSuccess();
        if(accountService.checkEmailExists(signUpRequest.getEmail())){
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);

        }
        signUpRequest.setRoleName("ROLE_ADMIN");
        success.setStatus(HttpStatus.OK.value());
        success.setData(accountService.signUp(signUpRequest));
        return new ResponseEntity<>(success,HttpStatus.OK);

    }

    @PostMapping("/api/forgot_password")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> forgotPassword(@RequestBody ResetPasswordDTO resetPasswordDTOl) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Send reset password is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Send reset password is completed !",
                        this.accountService.forgotPassword(resetPasswordDTOl.getEmail())
                )
        );
    }
    @PostMapping("/api/check-otp")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> checkOTP(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "OTP is: !",
                        this.accountService.validateTokenReset(resetPasswordDTO.getOtp(), resetPasswordDTO.getEmail())
                )
        );
    }
    @PutMapping("/api/set_password")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> setPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        System.out.println("Send reset password is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Send reset password is completed !",
                        this.accountService.setPassword(resetPasswordDTO)
                )
        );
    }

}
