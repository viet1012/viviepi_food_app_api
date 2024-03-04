package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.ResetPasswordDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.dto.token.RefreshTokenDTO;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;

public interface IAccountService {
    boolean checkEmailExists(String email);
    RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws CertificateException;
    ResponseObject signInAdmin(SignInRequest signInRequest);
    ResponseObject signInUser(SignInRequest signInRequest);
    UserSignUpDTO signUp(SignUpRequest request);
    String verifyEmail(UserSignUpDTO signUpDTO);
    String forgotPassword(String email)  throws UnsupportedEncodingException;
    String setPassword(ResetPasswordDTO resetPasswordDTO);
    String resendActiveAccount(String email) throws UnsupportedEncodingException;
}
