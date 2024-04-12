package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAll();

    ResponseOutput getAll(int page, int limit, String sortBy, String sortField);
    boolean checkEmailExists(String email);

    UserDTO findUserByEmail(String email);

    UserDTO addNewUser(long id);

    UserDTO showUserInfo(String email);

    long getIdByEmail(String emai);

    void deleteUserByUserId(String userId);

}
