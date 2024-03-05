package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.exceptions.AlreadyExistException;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseSuccess;
import com.food_app_api.Viviepi.services.AccountService;
import com.food_app_api.Viviepi.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/sign-in")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> SignIn(@RequestBody SignInRequest signInDTO) {

        return new ResponseEntity<>(accountService.signInAdmin(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> SignUp(@Valid @RequestBody SignUpRequest signUpRequest) throws AlreadyExistException, UnsupportedEncodingException {
        ResponseSuccess success = new ResponseSuccess();
        if(accountService.checkEmailExists(signUpRequest.getEmail())){
            throw new AlreadyExistException("Email is already exist!");
        }
        signUpRequest.setRoleName("USER");
        success.setStatus(HttpStatus.OK.value());
        success.setData(accountService.signUp(signUpRequest));
        return new ResponseEntity<>(success,HttpStatus.OK);

    }


}
