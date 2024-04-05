package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.ResetPasswordDTO;
import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.dto.token.RefreshTokenDTO;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import jakarta.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;

public interface IAccountService {

    UserDTO getUserInfo(String token);
    boolean checkEmailExists(String email);
    RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws CertificateException;
    @Transactional(rollbackFor = Exception.class)
    ResponseObject signInAdmin(SignInRequest signInRequest);
    @Transactional(rollbackFor = Exception.class)
    ResponseObject signInUser(SignInRequest signInRequest);
    UserSignUpDTO signUp(SignUpRequest request) throws Exception;
    String verifyEmail(UserSignUpDTO signUpDTO);
    String forgotPassword(String email) throws UnsupportedEncodingException, MessagingException;
    String setPassword(ResetPasswordDTO resetPasswordDTO);
    String resendActiveAccount(String email) throws UnsupportedEncodingException;
}
