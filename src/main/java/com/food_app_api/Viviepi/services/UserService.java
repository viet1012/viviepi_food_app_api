package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseSuccess;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean checkEmailExists(String email);

    UserDTO findUserByEmail(String email);

    UserDTO addNewUser(long id);

    UserDTO showUserInfo(String email);

    long getIdByEmail(String emai);


}
